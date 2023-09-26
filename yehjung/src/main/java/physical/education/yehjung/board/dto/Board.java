package physical.education.yehjung.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import physical.education.yehjung.board.boardEnum.Sports;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardPk;

    @Enumerated(EnumType.ORDINAL)
    private Sports sportsCdPk;

    private String boardNm;





}
