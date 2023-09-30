package pe.pecommunity.domain.post.application;

import static pe.pecommunity.global.error.ErrorCode.BOARD_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.MEMBER_NOT_EXIST;
import static pe.pecommunity.global.error.ErrorCode.NOT_AUTHORIZED;
import static pe.pecommunity.global.error.ErrorCode.NOT_LOGIN;
import static pe.pecommunity.global.error.ErrorCode.POST_NOT_EXIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import pe.pecommunity.domain.File.dao.FileRepository;
import pe.pecommunity.domain.File.domain.File;
import pe.pecommunity.domain.board.dao.BoardRepository;
import pe.pecommunity.domain.board.domain.Board;
import pe.pecommunity.domain.member.dao.MemberRepository;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.dao.PostRepository;
import pe.pecommunity.domain.post.dao.PostSpecification;
import pe.pecommunity.domain.post.domain.Post;
import pe.pecommunity.domain.post.dto.PostDto;
import pe.pecommunity.domain.post.dto.PostRequestDto;
import pe.pecommunity.domain.post.dto.PostSearchRequestDto;
import pe.pecommunity.global.error.exception.BaseException;
import pe.pecommunity.global.util.SecurityUtil;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public Long resister(PostRequestDto postRequest) {

        // 엔티티 조회
        Member member = memberRepository.findById(postRequest.getMemberId())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_EXIST));
        Board board = boardRepository.findById(postRequest.getBoardId())
                .orElseThrow(() -> new BaseException(BOARD_NOT_EXIST));

        // 게시글 생성
        Post post = Post.createPostBuilder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .member(member)
                .board(board)
                .build();

        postRepository.save(post);

        return post.getId();
    }

    @Transactional
    public Long update(Long postId, PostRequestDto postRequest) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_NOT_EXIST));

        SecurityUtil.checkAuthorizedMember(findPost.getMember().getMemberId());

        findPost.update(postRequest.getTitle(), postRequest.getContent());
        return findPost.getId();
    }

    @Transactional
    public void delete(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_NOT_EXIST));

        SecurityUtil.checkAuthorizedMember(findPost.getMember().getMemberId());

        postRepository.deleteById(postId);
    }

    /**
     * 게시글 조회
     */
    public PostDto findOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_NOT_EXIST));
        return PostDto.of(post);
    }

    /**
     * 게시글 검색
     */
    public List<PostDto> search(PostSearchRequestDto requestDto) {
        if(requestDto == null) {
            return postRepository.findAll().stream().map(p -> PostDto.of(p)).collect(Collectors.toList());
        }

        Map<String, String> searchKeys = new HashMap<>();

        if (requestDto.getMemberId() != null) searchKeys.put("memberId", requestDto.getMemberId());
        if (requestDto.getTitle() != null) searchKeys.put("title", requestDto.getTitle());
        if (requestDto.getContent() != null) searchKeys.put("content", requestDto.getContent());

        return postRepository.findAll(PostSpecification.searchPost(searchKeys))
                .stream().map(p -> PostDto.of(p))
                .collect(Collectors.toList());
    }
}
