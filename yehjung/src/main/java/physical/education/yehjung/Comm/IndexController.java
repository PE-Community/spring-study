package physical.education.yehjung.Comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import physical.education.yehjung.Member.dto.Authority;
import physical.education.yehjung.Member.dto.User;
import physical.education.yehjung.Member.repository.UserRepository;
import physical.education.yehjung.auth.PrincipalDetails;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication,
                                          @AuthenticationPrincipal UserDetails userDetails){ // DI
        System.out.println("/test/login =============");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication: "+principalDetails);
        System.out.println("userDetails: " + userDetails.getUsername());
        return "세션 정보 확인";
    }
    /*
     * 스프링 시큐리티
     * server가 관리하는 세션 내부에 시큐리티 세션이 있음
     * 시큐리티 세션에 들어갈 수 있는 타입은 authentication 밖에 없음
     * authentication은 두가지 UserDeatils 와 Oauth2User
     * UserDetails 는 일반 로그인, Oauth2User는 Oauth2 로그인
     * */
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth2){// DI(의존성주입)
        // @AuthenticationPrincipal이라는 어노테이션을 통해서 세션정보를 받을 수 있다.
        System.out.println("=====IndexController.testLogin====");
        System.out.println(authentication.getPrincipal());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        System.out.println("oauth2User: "+ oauth2.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }

    //localhost:8080
    //localhost:8080/
    @GetMapping({"","/"})
    public String index(){
        //mustache 기본 위치 main/src/resources/
        //뷰 리졸버 : templates (prefix)
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    //별도의 처리 없으면 스프링 시큐리티에서 해당 페이지 주소를 선점
    //security 파일 생성후 작동안함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }


    @PostMapping("/join")
    public String join(User user){
        user.setRole(Authority.USER);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);// 회원가입은 잘 되지만 암호화 되어있지 않아 로그인이 할 수 없음
        System.out.println(user);

        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){

        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){

        return "data info";
    }


}
