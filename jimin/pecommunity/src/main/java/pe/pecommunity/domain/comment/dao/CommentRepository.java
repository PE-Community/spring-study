package pe.pecommunity.domain.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.pecommunity.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
