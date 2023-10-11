package pe.pecommunity.domain.member.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "회원 아이디는 필수 값입니다.")
    private String memberId;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String password;
}
