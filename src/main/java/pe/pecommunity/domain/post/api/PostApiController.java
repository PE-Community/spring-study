package pe.pecommunity.domain.post.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.pecommunity.domain.File.FileStore;
import pe.pecommunity.domain.File.application.FileService;
import pe.pecommunity.domain.File.domain.UploadFile;
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
    private final FileService fileService;

    /**
     * 게시글 등록
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> register(@RequestPart(value = "post") @Valid PostRequestDto post, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        Long postId = postService.resister(post);
        if(files != null) {
            fileService.saveFiles(postId, files);
        }
        return ResponseUtils.success("게시글 등록 성공");
    }

    /**
     * 게시글 수정
     */
    @PatchMapping(value = "/{postId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> update(@PathVariable Long postId, @RequestPart @Valid PostRequestDto post,
                                 @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        postService.update(postId, post);
        if(files != null) {
            fileService.update(postId, files);
        }
        return ResponseUtils.success("게시글 수정 성공");
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> delete(@PathVariable Long postId) {
        fileService.delete(postId);
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
