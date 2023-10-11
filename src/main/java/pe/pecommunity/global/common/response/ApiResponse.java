package pe.pecommunity.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApiResponse<T> {
    private ResponseStatus status;
    private T data;
    private String message;

    @Builder
    public ApiResponse(ResponseStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
