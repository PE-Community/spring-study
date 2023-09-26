package physical.education.yehjung.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import physical.education.yehjung.post.dto.Post;
import physical.education.yehjung.post.repository.PostRepository;
import physical.education.yehjung.post.service.PostService;

import java.util.List;
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/find/list")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/find/{id}")
    public Post findPost(@PathVariable Long id) { // @PathVariable 웹 url 경로에서 변수 추출 어노테이션
        return postService.getPostById(id);
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
