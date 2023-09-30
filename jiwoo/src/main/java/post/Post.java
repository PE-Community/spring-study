package post;

import board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import member.Member;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name="POST")
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POST_PK")
    private long post_pk;

    @ManyToOne
    @JoinColumn(name="SPORTS_CD_FK")
    private Board sports_cd_fk;

    @ManyToOne
    @JoinColumn(name="MEMBER_FK")
    private Member member_fk;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    private LocalDateTime create_ymd;

    private LocalDateTime update_ymd;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "VIEW_CNT", nullable = false)
    @ColumnDefault("0")
    private int view_cnt;

    @Builder
    public Post(String title, LocalDateTime create_ymd, String content, LocalDateTime update_ymd) {
        this.title = title;
        this.content = content;
        this.create_ymd = create_ymd;
        this.update_ymd = update_ymd;
    }
}
