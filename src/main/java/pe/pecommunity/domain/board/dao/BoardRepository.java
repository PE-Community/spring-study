package pe.pecommunity.domain.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.pecommunity.domain.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
