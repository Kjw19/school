package sm.school.dao.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.BoardRepository;
import sm.school.domain.board.Board;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBoardDao implements BoardDao {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public Board findBoardById(Long id) {
        return boardRepository.findBoardById(id);
    }

    @Override
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return boardRepository.existsById(id);
    }

    @Override
    public List<Board> findBoardByMemberUserId(String id) {
        return boardRepository.findBoardByMemberUserId(id);
    }
}
