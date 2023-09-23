package post;

import files.FileService;
import files.StoreFile;
import files.UploadFileInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import member.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    private final StoreFile storeFile;
    private final PostRepository postRepository;

    @PostMapping("/create")
    public String createPost(@Valid ValidatePostRequestDto validatePostRequestDto, BindingResult bindingResult
            , HttpServletRequest request
            , @RequestParam List<MultipartFile> file) throws IOException {
        if(bindingResult.hasErrors()) {
            return "fail"; //게시글 작성 페이지로 리다이렉트
        }
        List<UploadFileInfo> uploadFileInfos = storeFile.storeFiles(file);
        postService.enrollPost(validatePostRequestDto);
        fileService.enrollFiles(uploadFileInfos);
        return "success";
    }

    @DeleteMapping("/delete/{post_pk}")
    public String deletePost(@PathVariable long post_pk, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.deletePost(post_pk,member);
        fileService.deleteFile(post_pk);
        return "success";
    }

    @PatchMapping("/update/{post_pk}")
    public String updatePost(@PathVariable long post_pk,
                             @Valid ValidatePostRequestDto validatePostRequestDto,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             @RequestParam List<MultipartFile> file) throws IOException {

        if(bindingResult.hasErrors()) {
            return "fail"; //게시글 수정 페이지로 리다이렉트
        }
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<UploadFileInfo> uploadFileInfos = storeFile.storeFiles(file);
        postService.updatePost(post_pk, validatePostRequestDto, member);
        fileService.updateFile(post_pk, uploadFileInfos);
        return "success";
    }

    @GetMapping("/searchTitle")
    public String searchPostByTitle(HttpServletRequest request, Model model) {
        String titleKeyWord = request.getParameter("title");
        List<Post> byTitleContaining = postRepository.findByTitleContaining(titleKeyWord);
        model.addAttribute("result", byTitleContaining);
        return "";
    }
    @GetMapping("/searchContent")
    public String searchPostByContent(HttpServletRequest request, Model model) {
        String contentKeyWord = request.getParameter("content");
        List<Post> byContentContaining = postRepository.findByContentContaining(contentKeyWord);
        model.addAttribute("result", byContentContaining);
        return "";
    }
    @GetMapping("/searchTitleAndContent")
    public String searchPostByTAndC(HttpServletRequest request, Model model) {
        String titleAndContent = request.getParameter("titleAndContent");
        List<Post> byTitleContainingOrContentContaining = postRepository.findByTitleContainingOrContentContaining(titleAndContent);
        model.addAttribute("result", byTitleContainingOrContentContaining);
        return "";
    }
    @GetMapping("/searchNickname")
    public String searchPostByNickname(String keyword, HttpServletRequest request, Model model) {
        String nickname = request.getParameter("nickname");
        List<Post> byNickname = postRepository.findByNickname(nickname);
        model.addAttribute("result", byNickname);
        return "";
    }
}
