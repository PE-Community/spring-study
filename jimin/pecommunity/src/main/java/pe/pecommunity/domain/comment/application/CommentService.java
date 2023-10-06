package pe.pecommunity.domain.comment.application;

import static pe.pecommunity.global.error.ErrorCode.COMMENT_LEVEL_EXCEED;
import static pe.pecommunity.global.error.ErrorCode.COMMENT_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.COMMENT_NOT_SAME_POST;
import static pe.pecommunity.global.error.ErrorCode.MEMBER_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.POST_NOT_EXIST;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.pecommunity.domain.comment.dao.CommentRepository;
import pe.pecommunity.domain.comment.domain.Comment;
import pe.pecommunity.domain.comment.dto.CommentRequestDto;
import pe.pecommunity.domain.member.dao.MemberRepository;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.dao.PostRepository;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.global.error.exception.BaseException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_COMMENT_LEVEL = 2;

    @Transactional
    public Long save(Long postId, CommentRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_EXIST));

        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_NOT_EXIST));

        Comment comment = Comment.createCommentBuilder()
                .content(requestDto.getContent())
                .member(member)
                .post(findPost)
                .build();

        if(requestDto.getIsSecret() != null) {
            comment.changeSecret(requestDto.getIsSecret());
        }

        if(requestDto.getParentId() != null) { // 대댓글인 경우
            Comment parent = getParent(postId, requestDto.getParentId());
            comment.addParent(parent);
        }

        if(comment.getStep() > MAX_COMMENT_LEVEL) { // 댓글 레벨 제한
            throw new BaseException(COMMENT_LEVEL_EXCEED);
        }

        commentRepository.save(comment);
        return comment.getId();
    }

    private Comment getParent(Long postId, Long parentId) {
        Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new BaseException(COMMENT_NOT_EXIST));
        if(parent.getPost().getId() != postId) { // 부모와 자식 댓글의 게시글이 같은지 확인
            throw new BaseException(COMMENT_NOT_SAME_POST);
        }
        return parent;
    }
}
