package pe.pecommunity.domain.comment.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.pecommunity.domain.comment.application.CommentService;
import pe.pecommunity.domain.comment.dto.CommentRequestDto;
import pe.pecommunity.global.common.response.ApiResponse;
import pe.pecommunity.global.common.response.ResponseUtils;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> register(@PathVariable Long postId, @Valid @RequestBody CommentRequestDto requestDto) {
        commentService.save(postId, requestDto);
        return ResponseUtils.success("댓글 등록 성공");
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> update(@PathVariable Long commentId, @Valid @RequestBody CommentRequestDto requestDto) {
        commentService.update(commentId, requestDto);
        return ResponseUtils.success("댓글 수정 성공");
    }

}
