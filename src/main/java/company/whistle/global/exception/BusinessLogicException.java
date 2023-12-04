package company.whistle.global.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {
    @Getter
    private Exceptions exceptions;

    public BusinessLogicException(Exceptions exceptions) {
        super(exceptions.getMessage());
        this.exceptions = exceptions;
    }
}