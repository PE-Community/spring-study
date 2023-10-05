package pe.pecommunity.domain.comment.application;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pe.pecommunity.domain.board.dao.BoardRepository;
import pe.pecommunity.domain.comment.dao.CommentRepository;
import pe.pecommunity.domain.comment.domain.Comment;
import pe.pecommunity.domain.comment.dto.CommentRequestDto;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.domain.Post;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired EntityManager em;
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 댓글_등록() throws Exception {
        //given
        Member member = createMember();
        Post post = createPost(member);

        String content = "댓글 내용입니다.";

        //when
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content(content)
                .memberId(member.getId())
                .build();

        Long commentId = commentService.save(post.getId(), commentRequestDto);

        //then
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        assertEquals("댓글 작성한 회원의 이름", "testname3", comment.getMember().getNickname());
        assertEquals("댓글의 게시글 타이틀", "방가방가", comment.getPost().getTitle());
    }

    @Test
    public void 대댓글_등록() throws Exception {
        //given
        Member member = createMember();
        Post post = createPost(member);
        Comment parent = createParentComment(member, post);

        String content = "두 번째 댓글";

        //when
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content(content)
                .memberId(member.getId())
                .parentId(parent.getId())
                .build();
        Long commentId = commentService.save(post.getId(), commentRequestDto);

        //then
        Comment child = commentRepository.findById(commentId).orElseThrow();
        assertEquals("부모 댓글 내용", parent.getContent(), child.getParent().getContent());
        assertEquals("자식 댓글 내용", "두 번째 댓글", parent.getChildren().get(0).getContent());
    }

    private Comment createParentComment(Member member, Post post) {
        Comment parent = Comment.createCommentBuilder()
                .content("첫 댓글")
                .member(member)
                .post(post)
                .build();
        em.persist(parent);
        return parent;
    }

    private Post createPost(Member member) {
        Post post = Post.createPostBuilder()
                .title("방가방가")
                .content("안녕하세요")
                .member(member)
                .board(boardRepository.findById(1L).get())
                .build();
        em.persist(post);
        return post;
    }

    private Member createMember() {
        Member member = Member.createMemberBuilder()
                .memberId("test3")
                .nickname("testname3")
                .password("test1234!")
                .email("test3@test.com")
                .build();
        em.persist(member);
        return member;
    }

}