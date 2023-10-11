package pe.pecommunity.domain.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.pecommunity.domain.post.domain.Post;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_cd_pk")
    private Long id;

//    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String boardNm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sports sports;

    @Builder(builderMethodName = "createBoardBuilder")
    public Board(String boardNm, Sports sports) {
        this.boardNm = boardNm;
        this.sports = sports;
    }
}
