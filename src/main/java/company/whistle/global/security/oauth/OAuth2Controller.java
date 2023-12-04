package company.whistle.global.security.oauth;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OAuth2Controller {
    @GetMapping("/loading")
    public ResponseEntity loginError(@RequestParam String refreshToken) {
        if(refreshToken.isEmpty()) {
            throw new BusinessLogicException(Exceptions.USER_EXISTS);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/oauth")
    public String login() {
        return "oauth-login";
    }
}