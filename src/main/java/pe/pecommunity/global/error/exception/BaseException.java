package pe.pecommunity.global.error.exception;


import lombok.Getter;
import pe.pecommunity.global.error.ErrorCode;

@Getter
public class BaseException extends RuntimeException {
    private String errorMessage;

    public BaseException(ErrorCode errorCode) {
        this.errorMessage = errorCode.getMessage();
    }
}
