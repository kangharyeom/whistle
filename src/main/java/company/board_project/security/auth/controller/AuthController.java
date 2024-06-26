package company.board_project.security.auth.controller;

import company.board_project.security.auth.dto.AuthEmailConfirmDto;
import company.board_project.security.auth.dto.AuthEmailDto;
import company.board_project.security.auth.dto.AuthEmailQuestionDto;
import company.board_project.security.auth.dto.AuthPasswordDto;
import company.board_project.security.auth.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    /*
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") @NotBlank String accessToken) {
        authService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
     * 토큰 재발급
     */
    @PostMapping("/token")
    public ResponseEntity refreshAccessToken(@RequestHeader("Refresh") @NotBlank String refreshToken,
                                             HttpServletResponse response) {

        String newAccessToken = authService.reissuedToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 비밀번호 찾기시 랜덤 링크 전송
     */
    @PostMapping("/password")
    public ResponseEntity findPassword(@RequestBody @Valid AuthEmailDto emailDto) throws MessagingException {
        authService.sendEmailForPassword(emailDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 랜덤 링크를 통한 비밀번호 변경
     */
    @PatchMapping("/password")
    public ResponseEntity authPasswordUrl(@RequestParam @NotBlank String authCode,
                                          @RequestBody @Valid AuthPasswordDto passwordDto) {
        authService.setNewPassword(authCode, passwordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 회원가입 이메일 인증번호 전송
     */
    @PostMapping("/email")
    public ResponseEntity authEmail(@RequestBody @Valid AuthEmailDto emailDto) throws MessagingException {
        authService.sendEmailForJoin(emailDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 인가코드 서버에 전송
     */
    @PostMapping("/email/confirm")
    public ResponseEntity confirmEmail(@RequestBody @Valid AuthEmailConfirmDto confirmDto) {
        authService.authorizedEmail(confirmDto.getAuthCode(), confirmDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 관리자에게 문의하기
     */
    @PostMapping("/email/query")
    public ResponseEntity queryEmail(@RequestBody @Valid AuthEmailQuestionDto questionDto) throws MessagingException {
        authService.sendEmailForQuery(questionDto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}