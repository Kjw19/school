package sm.school.dao.board;

import sm.school.domain.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDao {

    List<Board> findAll();

    Board save(Board board);

    Optional<Board> findById(Long id);

    Board findBoardById(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);
}
