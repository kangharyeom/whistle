package company.whistle.global.constant;

import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import lombok.Getter;

import java.util.Arrays;

public enum LoginType {
    BASIC("일반 로그인"),
    SOCIAL("소셜 로그인");

    @Getter
    private String loginType;

    LoginType(String loginType) { this.loginType = loginType; }

    public static LoginType verifiedLoginType(String data) {
        return Arrays.stream(values())
                .filter(status -> data.trim().toUpperCase().equals(status.toString()))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(Exceptions.INVALID_VALUES));
    }
}