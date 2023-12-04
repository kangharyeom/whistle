package company.whistle.global.security.auth.service;

import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.global.security.utils.CustomAuthorityUtils;
import company.whistle.global.security.utils.RedisUtils;
import company.whistle.global.security.jwt.component.JwtTokenizer;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.repository.UserRepository;
import company.whistle.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final JwtTokenizer jwtTokenizer;
    private final RedisUtils redisUtils;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String teamEmail;


    public void logout(String accessToken) {
        String email = userService.getLoginUser().getEmail();
        String key = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String token = accessToken.replace("Bearer ", "");
        Long atTime = jwtTokenizer.getExpiration(token, key);

        redisUtils.setBlackList(token, email + "expired_access", atTime);
        redisUtils.delete("refresh_" + email);
    }

    public String reissuedToken(String refreshToken) {
        String encodeBase64SecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        try {
            jwtTokenizer.verifySignature(refreshToken, encodeBase64SecretKey);
        } catch (
//                SignatureException |
                 MalformedJwtException | DecodingException e) {
            throw new BusinessLogicException(Exceptions.INVALID_VALUES);
        } catch (ExpiredJwtException e) {
            throw new BusinessLogicException(Exceptions.EXPIRED_JWT_TOKEN);
        }

        Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, encodeBase64SecretKey);
        String subject = claims.getBody().getSubject();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        if (!redisUtils.hasKey("refresh_" + subject) || !redisUtils.get("refresh_" + subject).equals(refreshToken)) {
            throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
        }

        Map<String, Object> map = new HashMap<>();
        List<String> roles = authorityUtils.createRoles(subject);
        map.put("username", subject);
        map.put("roles", roles);

        return jwtTokenizer.generateAccessToken(map, subject, expiration, encodeBase64SecretKey);
    }

    public void setNewPassword(String randomCode,
                               String newPassword) {
        if (redisUtils.get(randomCode) != null) {
            String email = redisUtils.get(randomCode).toString().substring(8);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
            user.setPassword(passwordEncoder.encode(newPassword));
            redisUtils.delete(randomCode);
        } else {
            throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
        }
    }

    public void authorizedEmail(String authCode, String email) {
        if (redisUtils.get("join_" + email) == null) {
            throw new BusinessLogicException(Exceptions.EMAIL_AUTH_REQUIRED);
        } else if (!redisUtils.get("join_" + email).equals("confirm")) {
            if (redisUtils.get("join_" + email).equals(authCode)) {
                redisUtils.set("join_" + email, "confirm", 10);
            } else {
                throw new BusinessLogicException(Exceptions.INVALID_EMAIL_AUTH_NUMBER);
            }
        }
    }

    private String createAuthCode() {
        SecureRandom random = new SecureRandom();
        String key = "";

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(25) + 65;
            key += (char) index;
        }

        int numIndex = random.nextInt(9999) + 1000;
        key += numIndex;

        return key;
    }

    private MimeMessage createNewPasswordForm(String userEmail) throws MessagingException {
        String authCode = createAuthCode();
        redisUtils.set(authCode, "PW_code_" + userEmail, 10);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, userEmail);
        mimeMessage.setSubject("[whistle] 비밀번호 변경 안내");
        mimeMessage.setFrom(teamEmail);
        mimeMessage.setText(setPasswordContext(authCode), "utf-8", "html");
        return mimeMessage;
    }

    private MimeMessage createJoinForm(String userEmail) throws MessagingException {
        String authCode = createAuthCode();
        redisUtils.set("join_" + userEmail, authCode, 3);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, userEmail);
        mimeMessage.setSubject("[whistle] 회원가입 이메일 인증");
        mimeMessage.setFrom(teamEmail);
        mimeMessage.setText(setJoinContext(authCode), "utf-8", "html");
        return mimeMessage;
    }

    private MimeMessage createQueryForm(String content) throws MessagingException {
        String userEmail = userService.getLoginUser().getEmail();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, teamEmail);
        mimeMessage.setSubject(userEmail + " 회원으로부터 문의사항이 도착했습니다.");
        mimeMessage.setText(userEmail + " 회원으로부터 문의사항이 도착했습니다."
                + "<br>" + "문의내용 : " + content, "utf-8", "html");
        return mimeMessage;
    }


    private String setPasswordContext(String authCode) {
        Context context = new Context();
        context.setVariable("link", "https://ec2-3-36-251-38.ap-northeast-2.compute.amazonaws.com:8080/auth/password?authCode=" + authCode);
        context.setVariable("authCode", authCode);
        return templateEngine.process("NewPassword", context);
    }

    private String setJoinContext(String authCode) {
        Context context = new Context();
        context.setVariable("code", authCode);
        return templateEngine.process("Join", context);
    }

    public void sendEmailForPassword(String userEmail) throws MessagingException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
        userService.isSocialUser(user);
        MimeMessage emailForm = createNewPasswordForm(userEmail);
        javaMailSender.send(emailForm);
    }

    public void sendEmailForJoin(String userEmail) throws MessagingException {
        userService.verifiedUser(userEmail);
        MimeMessage emailForm = createJoinForm(userEmail);
        javaMailSender.send(emailForm);
    }

    public void sendEmailForQuery(String content) throws MessagingException {
        MimeMessage queryForm = createQueryForm(content);
        javaMailSender.send(queryForm);
        log.info("회원으로부터 문의메일 도착");
    }
}