package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.school.Repository.BoardRepository;
import sm.school.domain.board.Board;
import sm.school.dto.BoardDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board createBoard(BoardDTO boardDTO) {
        Board board = boardDTO.toBoard();

        return boardRepository.save(board);
    }

    public List<Board> findBoard() {
        List<Board> boardList = boardRepository.findAll();

        return boardList;
    }

    public BoardDTO selectBoard(long id) {
        Board board = boardRepository.findBoardById(id);

        BoardDTO boardDTO = board.toBoardDTO();

        return boardDTO;
    }
}
