package pe.pecommunity.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1781043865L;

    public static final QMember member = new QMember("member1");

    public final ListPath<pe.pecommunity.domain.comment.domain.Comment, pe.pecommunity.domain.comment.domain.QComment> comments = this.<pe.pecommunity.domain.comment.domain.Comment, pe.pecommunity.domain.comment.domain.QComment>createList("comments", pe.pecommunity.domain.comment.domain.Comment.class, pe.pecommunity.domain.comment.domain.QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> dormantConversionDate = createDateTime("dormantConversionDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberId = createString("memberId");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<pe.pecommunity.domain.post.domain.Post, pe.pecommunity.domain.post.domain.QPost> posts = this.<pe.pecommunity.domain.post.domain.Post, pe.pecommunity.domain.post.domain.QPost>createList("posts", pe.pecommunity.domain.post.domain.Post.class, pe.pecommunity.domain.post.domain.QPost.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

