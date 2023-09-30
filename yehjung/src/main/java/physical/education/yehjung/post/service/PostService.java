package physical.education.yehjung.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import physical.education.yehjung.Member.dto.User;
import physical.education.yehjung.Member.repository.UserRepository;
import physical.education.yehjung.post.dto.Post;
import physical.education.yehjung.post.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;



    public List<Post> getPostList() {

        return postRepository.findAll();
    }


    @Transactional
    public Post getPostById(Long postPk) {
        Post post = postRepository.findById(postPk).get();

        Post dto = Post.builder()
                .postPk(post.getPostPk())
                .author(post.getAuthor())
                .title(post.getTitle())
                .content(post.getContent())
                .fileId(post.getFileId())
                .createYmd(post.getCreateYmd())
                .build();
        return dto;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public boolean checkMyPost(Long id, String nickname){
        Post post = postRepository.findById(id).orElse(null);
        if(post != null) {
            User user = userRepository.findByNickname(nickname);
            return post.getMemberFk().equals(user.getNickname());
        }
        return false;
    }

    public void incrementViewCount(Long postPk) {
        Post post = postRepository.findById(postPk).orElse(null);
        if (post != null) {
            post.setViewCnt(post.getViewCnt() + 1);
            postRepository.save(post);
        }
    }

    @Transactional
    public Long savePost(Post post) {
        return postRepository.save(post.toEntity()).getPostPk();
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
