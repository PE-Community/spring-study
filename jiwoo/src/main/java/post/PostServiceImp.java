package post;

import files.FileRepository;
import files.UploadFileInfo;
import lombok.RequiredArgsConstructor;
import member.Member;
import member.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Override
    public void enrollPost(ValidatePostRequestDto validatePostRequestDto) {
        Post createPost = Post.builder()
                .content(validatePostRequestDto.getContent())
                .title(validatePostRequestDto.getTitle())
                .create_ymd(LocalDateTime.now())
                .build();
        postRepository.save(createPost);

    }

    @Override
    public String deletePost(long post_pk, Member member) {
        List<Post> postList = postRepository.findPostByMemberPk(member.getMember_pk());
        Stream<Post> postStream = postList.stream().filter(m -> m.getPost_pk() == post_pk);
        if(postStream!=null) {
            postRepository.deleteById(post_pk);
        }
        return "해당 게시글을 삭제합니다.";
    }

    @Override
    public void updatePost(long post_pk, ValidatePostRequestDto validatePostRequestDto, Member member) {
        List<Post> postList = postRepository.findPostByMemberPk(member.getMember_pk());
        Stream<Post> postStream = postList.stream().filter(m -> m.getPost_pk() == post_pk);
        if(postStream!=null) {
            Post updatePost = Post.builder()
                    .content(validatePostRequestDto.getContent())
                    .title(validatePostRequestDto.getTitle())
                    .update_ymd(LocalDateTime.now())
                    .build();
            postRepository.save(updatePost);
        }
    }
}
