package pe.pecommunity.domain.post.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.pecommunity.domain.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
}
