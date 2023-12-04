package company.whistle.domain.user.service;

import company.whistle.domain.user.repository.UserRepository;
import company.whistle.global.constant.LeagueRole;
import company.whistle.global.constant.LoginType;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.global.security.utils.CustomAuthorityUtils;
import company.whistle.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;

    /*
    * 회원 가입
    * 회원가입시 role을 부여하고 비밀번호를 암호화함
    */
    public User createUser(User user) {
        try {
            verifiedUser(user.getEmail());

            List<String> roles = authorityUtils.createRoles(user.getEmail());
            user.setRoles(roles);

            String encryptPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptPassword);

            userRepository.save(user);
            log.info("USER POST COMPLETE: {}", user.toString());
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.USER_NOT_CREATED);
        }
        return user;
    }

    // 회원 수정
    public User updateUser(User user, Long userId) {
        try {
            if (userId == null) {
                log.info("userId: {}", userId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            // 회원 유무 확인
            User findUser = existUser(userId);

            // 새로 변경할 이메일 존재 유무 확인
            Optional.ofNullable(user.getEmail())
                    .ifPresent(newEmail -> {
                        verifiedUser(findUser.getEmail());
                        findUser.setEmail(newEmail);
                    });

            Optional.ofNullable(user.getPassword())
                    .ifPresent(findUser::setPassword);

            Optional.ofNullable(user.getName())
                    .ifPresent(findUser::setName);
            userRepository.save(findUser);
            log.info("USER PATCH COMPLETE: {}", user.toString());
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.USER_NOT_PATCHED);
        }
        return user;
    }

    // 회원 단건 조회
    public User findUser(Long userId) {
        return existUser(userId);
    }

    // 회원 전체 조회
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    // 회원 탈퇴
    public void deleteUser(Long userId) {
        try {
            User findUser = existUser(userId);
            userRepository.delete(findUser);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.USER_NOT_DELETED);
        }
    }

    // 회원 존재 유무 확인 메서드
    public User existUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }

    // 아이디, 이메일 중복 검사
    public void verifiedUser(String email) {
        Optional<User> userEmail = userRepository.findByEmail(email);
        if(userEmail.isPresent()) {
            throw new BusinessLogicException(Exceptions.EMAIL_EXISTS);
        }
    }

    public void verifiedLoginId(String loginId) {
        Optional<User> userLoginId = userRepository.findByLoginId(loginId);
        if(userLoginId.isPresent()) {
            throw new BusinessLogicException(Exceptions.LOGIN_ID_EXISTS);
        }
    }

    // 로그인한 회원
    public User getLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }

    // 본인만 접근 허용
    public void checkJwtAndUser(Long userId) {
        if (!getLoginUser().getUserId().equals(userId)) {
            throw new BusinessLogicException(Exceptions.ACCESS_FORBIDDEN);
        }
    }

    // 소셜 회원 확인
    public void isSocialUser(User user) {
        if (user.getLoginType().equals(LoginType.SOCIAL)) {
            throw new BusinessLogicException(Exceptions.ACCESS_FORBIDDEN);
        }
    }

    public User findVerifiedUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }

    public User findVerifiedUserByLeagueRole(LeagueRole leagueRole) {
        Optional<User> optionalUser = userRepository.findByLeagueRole(leagueRole);
        return optionalUser.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.UNAUTHORIZED));
    }

    public User findVerifiedUserByTeamMemberRole(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }
}
