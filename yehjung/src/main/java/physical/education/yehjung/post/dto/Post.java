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
    // 데이터 베이스 저장 시점 현재시간으로 설정
    @CreationTimestamp
    private LocalDateTime createYmd;
    // 업데이트 시점 때 자동으로 현재시간으로 변환
    @UpdateTimestamp
    private LocalDateTime updateYmd;

    private String content;

    @Column(name = "view_cnt")
    private int viewCnt;



}
