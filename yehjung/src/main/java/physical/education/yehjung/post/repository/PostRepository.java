package physical.education.yehjung.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import physical.education.yehjung.post.dto.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByTitleContaining(String title);

}
