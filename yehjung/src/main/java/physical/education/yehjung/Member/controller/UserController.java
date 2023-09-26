package physical.education.yehjung.Member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import physical.education.yehjung.Member.dto.User;
import physical.education.yehjung.Member.repository.UserRepository;

import java.util.Optional;

@RestController
public class UserController {

//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    //localhost:8080
//    //localhost:8080/
//    @GetMapping({"","/"})
//    public String index(){
//        //mustache 기본 위치 main/src/resources/
//        //뷰 리졸버 : templates (prefix)
//        return "index";
//    }
//
//    @GetMapping("/user")
//    public @ResponseBody String user(){
//        return "user";
//    }
//
//    @GetMapping("/admin")
//    public @ResponseBody String admin(){
//        return "admin";
//    }
//
//    @GetMapping("/manager")
//    public @ResponseBody String manager(){
//        return "manager";
//    }
//
//    //별도의 처리 없으면 스프링 시큐리티에서 해당 페이지 주소를 선점
//    //security 파일 생성후 작동안함
//    @GetMapping("/loginForm")
//    public String loginForm(){
//        return "loginForm";
//    }
//
//    @GetMapping("/login")
//    public String getLoginForm(){
//        return "loginForm";
//    }
//
//    @GetMapping("/joinForm")
//    public String joinForm(){
//        return "joinForm";
//    }
//
//
//    @PostMapping("/join")
//    public String join(User user){
//        user.setRole("ROLE_USER");
//        String rawPassword = user.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//        user.setPassword(encPassword);
//        userRepository.save(user);// 회원가입은 잘 되지만 암호화 되어있지 않아 로그인이 할 수 없음
//        System.out.println(user);
//
//        return "redirect:/";
//    }
//
//    @Secured("ROLE_ADMIN")
//    @GetMapping("/info")
//    public @ResponseBody String info(){
//
//        return "개인정보";
//    }
//
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    @GetMapping("/data")
//    public @ResponseBody String data(){
//
//        return "data info";
//    }

}
