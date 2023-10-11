package pe.pecommunity.domain.post.application;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pe.pecommunity.domain.board.dao.BoardRepository;
import pe.pecommunity.domain.board.domain.Board;
import pe.pecommunity.domain.board.domain.Sports;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.dao.PostRepository;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.domain.post.dto.PostRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired EntityManager em;
    @Autowired PostService postService;
    @Autowired PostRepository postRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 게시글_등록() throws Exception {
        //given
        Member member = createMember();
        Board board = boardRepository.findById(1L).get();
        String title = "제목입니다.";
        String content = "내용입니다.";

        //when
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .boardId(board.getId())
                .memberId(member.getId())
                .title(title)
                .content(content)
                .build();
        Long postId = postService.resister(postRequestDto);

        //then
        Post post = postRepository.findById(postId).orElseThrow();
        assertEquals("게시글 작성한 회원의 이름", "testname", post.getMember().getNickname());
        assertEquals("해당 게시글의 게시판 유형", Sports.SOCCER, post.getBoard().getSports());
    }

    private Board createBoard() {
        Board board = Board.createBoardBuilder()
                .boardNm("체육 게시판")
                .sports(Sports.SOCCER)
                .build();
        em.persist(board);
        return board;
    }

    private Member createMember() {
        Member member = Member.createMemberBuilder()
                .memberId("test1")
                .nickname("testname")
                .password("test1234!")
                .email("test@test.com")
                .build();
        em.persist(member);
        return member;
    }

}