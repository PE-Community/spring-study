package board;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name="BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long board_cd_pk;
    @Column
    private Sports sports_nm;
    @Column
    private String board_nm;
}
