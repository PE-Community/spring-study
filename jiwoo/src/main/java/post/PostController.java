package post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import member.Member;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;



@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public String createPost(@Valid ValidatePostRequestDto validatePostRequestDto, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "fail"; //게시글 작성 페이지로 리다이렉트
        }
        postService.enrollPost(validatePostRequestDto);
        return "success";
    }

    @DeleteMapping("/delete/{post_pk}")
    public String deletePost(@PathVariable long post_pk, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.deletePost(post_pk,member);
        return "success";
    }

    @PatchMapping("/update/{post_pk}")
    public String updatePost(@PathVariable long post_pk,
                             @Valid ValidatePostRequestDto validatePostRequestDto,
                             BindingResult bindingResult,
                             HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "fail"; //게시글 수정 페이지로 리다이렉트
        }
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.updatePost(post_pk, validatePostRequestDto, member);
        return "success";
    }
}
