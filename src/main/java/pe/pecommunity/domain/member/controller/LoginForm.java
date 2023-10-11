package pe.pecommunity.domain.member.controller;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
