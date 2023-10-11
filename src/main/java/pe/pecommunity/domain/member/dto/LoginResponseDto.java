package pe.pecommunity.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginResponseDto {
    private final String accessToken;

    @Builder
    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
