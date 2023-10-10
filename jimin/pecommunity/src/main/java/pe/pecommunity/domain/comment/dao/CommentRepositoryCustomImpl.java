package pe.pecommunity.domain.comment.dao;

import static pe.pecommunity.domain.comment.domain.QComment.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import pe.pecommunity.domain.comment.domain.Comment;


@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(
                    comment.parent.id.asc().nullsFirst(),
                    comment.createDate.asc()
                ).fetch();
    }
}
