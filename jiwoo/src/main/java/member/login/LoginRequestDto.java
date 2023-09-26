package member.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String loginPw;
}
