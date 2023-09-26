package physical.education.yehjung.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import physical.education.yehjung.Member.dto.User;
import physical.education.yehjung.Member.repository.UserRepository;
import physical.education.yehjung.post.dto.Post;
import physical.education.yehjung.post.repository.PostRepository;

import java.util.List;
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;



    public List<Post> getAllPosts() {


        return postRepository.findAll();
    }

    public Post getPostById(Long postPk) {
        return postRepository.findById(postPk).orElse(null);
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
    public void deletePost(Long postPk) {
        postRepository.deleteById(postPk);
    }

    public void incrementViewCount(Long postPk) {
        Post post = postRepository.findById(postPk).orElse(null);
        if (post != null) {
            post.setViewCnt(post.getViewCnt() + 1);
            postRepository.save(post);
        }
    }

}
