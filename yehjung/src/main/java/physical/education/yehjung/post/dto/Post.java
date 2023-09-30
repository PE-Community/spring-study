package physical.education.yehjung.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import physical.education.yehjung.board.dto.Board;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postPk;

    @ManyToOne
    @JoinColumn(name = "board_cd_fk")
    private Board board;

    @Column(name = "member_fk")
    private String memberFk;

    private String title;

    private String content;

    private String author;

    @Column
    private Long fileId;

    // 데이터 베이스 저장 시점 현재시간으로 설정
    @CreationTimestamp
    private LocalDateTime createYmd;
    // 업데이트 시점 때 자동으로 현재시간으로 변환
    @UpdateTimestamp
    private LocalDateTime updateYmd;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Builder
    public Post(Long id, String author, String title, String content, Long fileId) {
        this.postPk = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }

    public Post toEntity() {
        Post build = Post.builder()
                .postPk(postPk)
                .author(author)
                .title(title)
                .content(content)
                .fileId(fileId)
                .build();
        return build;
    }



}
