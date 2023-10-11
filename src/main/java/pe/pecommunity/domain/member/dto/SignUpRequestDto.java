package pe.pecommunity.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import pe.pecommunity.domain.member.domain.Member;

@Getter
public class SignUpRequestDto {
    @NotBlank(message = "회원 아이디는 필수 값입니다.")
    @Size(max = 10, message = "아이디는 10자이내여야 합니다.")
    @Pattern(regexp="^[a-z|A-Z|0-9]*$", message = "아이디는 영문 또는 숫자로만 구성되어야 합니다.")
    @Schema(description = "회원 아이디", example = "test1")
    private String memberId;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    @Size(max = 10, message = "닉네임은 10자이내여야 합니다.")
    @Pattern(regexp="^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]*$", message = "닉네임은 한글, 영문, 숫자로만 구성되어야 합니다.")
    @Schema(description = "닉네임", example = "testname")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15자 이여야 합니다.")
    @Pattern(regexp="(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&].{0,}", message = "비밀번호는 영문, 숫자, 특수문자로 구성되어야 합니다.")
    @Schema(description = "비밀번호", example = "test1234!")
    private String password;

    @Email(message = "이메일 형식을 맞춰주세요")
    @Schema(description = "이메일", example = "test@test.com")
    private String email;

    public Member toEntity() {
        return Member.createMemberBuilder()
                .memberId(memberId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}
