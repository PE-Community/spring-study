package post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByMemberPk(long member_pk);
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByContentContaining(String keyword);
    List<Post> findByTitleContainingOrContentContaining(String keyword);
    List<Post> findByNickname(String keyword);
}
