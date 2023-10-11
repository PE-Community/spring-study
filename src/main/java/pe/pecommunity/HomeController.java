package pe.pecommunity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.pecommunity.domain.member.domain.Member;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/loginHome")
    public String loginHome(Model model) {
        model.addAttribute("member", Member.createMemberBuilder().nickname("test").build());
        return "loginHome";
    }

    @RequestMapping("/login-test")
    public String test(Model model) {
        return "test";
    }
}

