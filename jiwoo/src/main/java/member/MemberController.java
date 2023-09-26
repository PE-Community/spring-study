package member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home() {
        return "hmoe"; // 메인 페이지로 이동
    }
    @GetMapping("/")
    public String homeByLogin(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if(loginMember==null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome"; //로그인 된 메인 페이지로 이동
    }
    @GetMapping("/join")
    public ResponseEntity<String> signUpForm(Model model) {
        model.addAttribute("signUpRequestDto", new SignUpRequestDto());
        return ResponseEntity.ok("");
    }

    @PostMapping("/join")
    public String signUp(@Valid SignUpRequestDto signUpDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "fail"; //회원가입 페이지로 재이동 해야함
        }
        Member member = Member.builder()
                .id(signUpDto.getId())
                .pw(signUpDto.getPw())
                .nickname(signUpDto.getNickname())
                .email(signUpDto.getEmail())
                .authority(Authority.MEMBER)
                .build();
        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "fail";
        }
        return"success";
    }
    @GetMapping("/join/{memberId}")
    public String checkDuplicateId(@PathVariable String memberId) {
        Optional<Member> findMemberId = memberRepository.findByMemberId(memberId);
        if(findMemberId != null) {
            return "fail";
        }
        return "success";
    }
}
