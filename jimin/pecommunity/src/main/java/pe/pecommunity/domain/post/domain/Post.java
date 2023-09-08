package pe.pecommunity.domain.post.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import pe.pecommunity.domain.board.domain.Board;
import pe.pecommunity.domain.member.domain.Member;

@Entity
@Table
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_pk")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_pk")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Long viewCnt;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder(builderMethodName = "createPostBuilder")
    public Post(Board board, Member member, String title, String content) {
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();

        //==연관관계 편의 메서드==//
        board.getPosts().add(this);
        member.getPosts().add(this);
    }
}
