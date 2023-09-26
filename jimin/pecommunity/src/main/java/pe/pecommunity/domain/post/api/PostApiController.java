package pe.pecommunity.domain.post.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.pecommunity.domain.post.application.PostService;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.domain.post.dto.PostDto;
import pe.pecommunity.domain.post.dto.PostRequestDto;
import pe.pecommunity.domain.post.dto.PostSearchRequestDto;
import pe.pecommunity.global.common.response.ApiResponse;
import pe.pecommunity.global.common.response.ResponseUtils;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 등록
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> register(@RequestBody @Valid PostRequestDto request) {
        postService.resister(request);
        return ResponseUtils.success("게시글 등록 성공");
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{postId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> update(@PathVariable Long postId, @RequestBody @Valid PostRequestDto request) {
        postService.update(postId, request);
        return ResponseUtils.success("게시글 수정 성공");
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{postId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseUtils.success("게시글 삭제 성공");
    }


    /**
     * 게시글 단일 조회
     */
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getPost(@PathVariable Long postId) {
        PostDto post = postService.findOne(postId);
        return ResponseUtils.successAsJson("post", post, "게시글 조회 성공");
    }

    /**
     * 게시글 검색
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> search(@RequestBody(required = false)  PostSearchRequestDto requestDto) {
        List<PostDto> postList = postService.search(requestDto);
        return ResponseUtils.successAsJson("post_list", postList, "게시글 전체 조회 성공");
    }
}
