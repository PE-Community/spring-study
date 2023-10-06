package pe.pecommunity.domain.comment.application;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
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

        //when
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("댓글 내용입니다.")
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
        Comment parent = createComment(member, post, "첫 번째 댓글");

        //when
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("두 번째 댓글")
                .memberId(member.getId())
                .parentId(parent.getId())
                .build();
        Long commentId = commentService.save(post.getId(), commentRequestDto);

        //then
        Comment child = commentRepository.findById(commentId).orElseThrow();
        assertEquals("부모 댓글 내용", parent.getContent(), child.getParent().getContent());
        assertEquals("자식 댓글 내용", "두 번째 댓글", parent.getChildren().get(0).getContent());
        assertEquals("부모 댓글 level", 0, parent.getStep());
        assertEquals("자식 댓글 level", 1, child.getStep());
    }

    private Comment createComment(Member member, Post post, String content) {
        Comment comment = Comment.createCommentBuilder()
                .content(content)
                .member(member)
                .post(post)
                .build();
        em.persist(comment);
        return comment;
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