package pe.pecommunity.global.common.response;

import java.util.Map;
import pe.pecommunity.global.error.ErrorCode;

public class ResponseUtils {

    /**
     * 성공
     */
    public static <T>ApiResponse<T> success (String message) {
        return success(null, message);
    }

    public static <T>ApiResponse<T> success (T data) {
        return success(data, null);
    }

    public static <T>ApiResponse success (T data, String message) {
        return ApiResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T>ApiResponse<T> successAsJson (String key, T object, String message) {
        return success(Map.of(key, object), message);
    }

    /**
     * 실패 - 회원가입, 로그인
     */
    public static <T>ApiResponse failure (T data, String message) {
        return ApiResponse.builder()
                .status(ResponseStatus.FAILURE)
                .data(data)
                .message(message)
                .build();
    }

    /**
     * 에러
     */
    public static <T>ApiResponse<T> error (T data, ErrorCode message) {
        return error(data, message.getMessage());
    }

    public static <T>ApiResponse error (T data, String message) {
        return ApiResponse.builder()
                .status(ResponseStatus.ERROR)
                .data(data)
                .message(message)
                .build();
    }
}
