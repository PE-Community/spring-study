package physical.education.yehjung.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import physical.education.yehjung.board.dto.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
