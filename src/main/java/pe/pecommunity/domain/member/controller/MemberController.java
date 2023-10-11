package pe.pecommunity.domain.member.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pe.pecommunity.domain.member.application.MemberService;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.member.domain.Role;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.createMemberBuilder()
                .memberId(form.getMemberId())
                .nickname(form.getNickname())
                .password(form.getPassword())
                .email(form.getEmail())
                .build();

        memberService.join(member);

        return "redirect:/members/login";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid LoginForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/loginForm";
        }

        memberService.login(form.getLoginId(), form.getPassword());

        log.info("[로그인 성공] - /members/login");

        return "redirect:/";
    }

    @PostMapping("/members/logout")
    public String logout() {
        log.info("[로그아웃] /members/logout");
        return "redirect:/";
    }
}
