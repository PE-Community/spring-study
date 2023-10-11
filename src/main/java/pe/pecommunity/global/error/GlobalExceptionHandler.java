package pe.pecommunity.global.error;

import static pe.pecommunity.global.error.ErrorCode.*;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.pecommunity.global.common.response.ApiResponse;
import pe.pecommunity.global.common.response.ResponseUtils;
import pe.pecommunity.global.error.exception.BaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 객체, 파라미터 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        for(FieldError er : bindingResult.getFieldErrors()) {
            if(errors.containsKey(er.getField())) {
                errors.put(er.getField(), errors.get(er.getField()) + " " + er.getDefaultMessage());
            }
            else {
                errors.put(er.getField(), er.getDefaultMessage());
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);

        log.error("[MethodArgumentNotValidException] " + data);

        return ResponseUtils.error(data, INVALID_TYPE_VALUE);
    }

    /**
     * 커스텀 예외 - 기본
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public ApiResponse<?> handleBaseException(BaseException e) {
        log.error("[BaseException] " + e.getErrorMessage());
        return ResponseUtils.failure(null, e.getErrorMessage());
    }
}
