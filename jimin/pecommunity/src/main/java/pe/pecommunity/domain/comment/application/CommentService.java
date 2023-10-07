package pe.pecommunity.domain.comment.application;

import static pe.pecommunity.global.error.ErrorCode.COMMENT_LEVEL_EXCEED;
import static pe.pecommunity.global.error.ErrorCode.COMMENT_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.COMMENT_NOT_SAME_POST;
import static pe.pecommunity.global.error.ErrorCode.COMMENT_REMOVE_FAIL;
import static pe.pecommunity.global.error.ErrorCode.MEMBER_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.PARENT_COMMENT_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.POST_NOT_EXIST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.pecommunity.domain.comment.dao.CommentRepository;
import pe.pecommunity.domain.comment.domain.Comment;
import pe.pecommunity.domain.comment.dto.CommentDto;
import pe.pecommunity.domain.comment.dto.CommentRequestDto;
import pe.pecommunity.domain.member.dao.MemberRepository;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.dao.PostRepository;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.global.error.exception.BaseException;
import pe.pecommunity.global.util.SecurityUtil;

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

        if(comment.getLevel() > MAX_COMMENT_LEVEL) { // 댓글 레벨 제한
            throw new BaseException(COMMENT_LEVEL_EXCEED);
        }

        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long update(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(COMMENT_NOT_EXIST));

        SecurityUtil.checkAuthorizedMember(comment.getMember().getMemberId());

        comment.update(requestDto.getContent());

        if(requestDto.getIsSecret() != null) {
            comment.changeSecret(requestDto.getIsSecret());
        }

        return comment.getId();
    }

    /**
     * 1. 최상위 댓글
     *     1. 대댓글이 없거나 모두 삭제된 대댓글인 경우 : 부모 및 모든 대댓글의 DB 삭제 O -> delete
     *     2. 삭제되지 않은 대댓글이 존재하는 경우: DB 삭제 X, "삭제된 댓글입니다"라고 표시 -> remove
     */
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(COMMENT_NOT_EXIST));

        SecurityUtil.checkAuthorizedMember(comment.getMember().getMemberId());

        comment.changeIsRemoved(true);

        deleteRootComment(comment); // 최상위 댓글 delete or not
    }

    /**
     * 게시글 댓글 전체 조회
     */
    public List<CommentDto> findAll(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_NOT_EXIST));

        return commentRepository.findAllByPostId(post.getId())
                .stream().map(c -> CommentDto.of(c))
                .collect(Collectors.toList());
    }

    private void deleteRootComment(Comment comment) {
        Comment root = findRootComment(comment);
        if(!root.getIsRemoved()) return;
        int cnt = countNotRemovedChild(root);
        if(cnt == 0) {
            commentRepository.deleteById(root.getId());
        }
    }

    private Comment findRootComment(Comment curr) {
        if(curr.getParent() == null) return curr;
        return findRootComment(curr.getParent());
    }

    private int countNotRemovedChild(Comment parent) {
        if(parent == null) return 0;

        int cnt = 0;
        if(parent.getChildren().size() > 0) {
            for (Comment child : parent.getChildren()) {
                if (!child.getIsRemoved()) cnt += 1;
                cnt += countNotRemovedChild(child);
            }
        }

        return cnt;
    }

    private Comment getParent(Long postId, Long parentId) {
        Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new BaseException(PARENT_COMMENT_NOT_EXIST));
        if(parent.getPost().getId() != postId) { // 부모와 자식 댓글의 게시글이 같은지 확인
            throw new BaseException(COMMENT_NOT_SAME_POST);
        }
        return parent;
    }
}
