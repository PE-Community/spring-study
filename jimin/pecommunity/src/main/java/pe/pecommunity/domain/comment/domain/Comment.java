package pe.pecommunity.domain.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.pecommunity.domain.member.domain.Member;
import pe.pecommunity.domain.post.domain.Post;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "member_pk")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_pk")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    private String content;
    private int step;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isDeleted;

    @Builder(builderMethodName = "createCommentBuilder")
    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
        this.step = 0;
        this.isDeleted = false;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();

        //==연관관계 편의 메서드==//
        post.getComments().add(this);
        member.getComments().add(this);
    }

    public void addParent(Comment parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

}
