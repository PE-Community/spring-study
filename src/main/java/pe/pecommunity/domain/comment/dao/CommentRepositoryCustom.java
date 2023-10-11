package pe.pecommunity.domain.comment.dao;

import java.util.List;
import pe.pecommunity.domain.comment.domain.Comment;

public interface CommentRepositoryCustom {
    List<Comment> findAllByPostId(Long postId);
}
