package pe.pecommunity.domain.member.controller;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 아이디는 필수입니다")
    private String memberId;

    @NotEmpty(message = "닉네임은 필수입니다")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password;

    private String email;
}
