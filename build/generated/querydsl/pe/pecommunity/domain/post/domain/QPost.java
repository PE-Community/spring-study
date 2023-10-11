package pe.pecommunity.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 175384947L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final pe.pecommunity.domain.QBaseTimeEntity _super = new pe.pecommunity.domain.QBaseTimeEntity(this);

    public final pe.pecommunity.domain.board.domain.QBoard board;

    public final ListPath<pe.pecommunity.domain.comment.domain.Comment, pe.pecommunity.domain.comment.domain.QComment> comments = this.<pe.pecommunity.domain.comment.domain.Comment, pe.pecommunity.domain.comment.domain.QComment>createList("comments", pe.pecommunity.domain.comment.domain.Comment.class, pe.pecommunity.domain.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<pe.pecommunity.domain.File.domain.File, pe.pecommunity.domain.File.domain.QFile> files = this.<pe.pecommunity.domain.File.domain.File, pe.pecommunity.domain.File.domain.QFile>createList("files", pe.pecommunity.domain.File.domain.File.class, pe.pecommunity.domain.File.domain.QFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final pe.pecommunity.domain.member.domain.QMember member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final NumberPath<Long> viewCnt = createNumber("viewCnt", Long.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new pe.pecommunity.domain.board.domain.QBoard(forProperty("board")) : null;
        this.member = inits.isInitialized("member") ? new pe.pecommunity.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

