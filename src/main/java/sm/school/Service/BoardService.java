package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.BoardRepository;
import sm.school.domain.board.Board;
import sm.school.dto.BoardDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public Board createBoard(BoardDTO boardDTO) {
        Board board = boardDTO.toBoard();

        return boardRepository.save(board);
    }

    public void updateBoard(BoardDTO boardDTO) {

        Board board = boardRepository.findBoardById(boardDTO.getId());

        board.updateBoard(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getPicture());
    }

    public List<Board> findBoard() {
        List<Board> boardList = boardRepository.findAll();

        return boardList;
    }

    public BoardDTO selectBoard(Long id) {
        Board board = boardRepository.findBoardById(id);

        BoardDTO boardDTO = board.toBoardDTO();

        return boardDTO;
    }

    public Boolean deleteBoard(Long id) {
        boolean check;
        try {
            boardRepository.deleteById(id);
            check = true;
        } catch (EmptyResultDataAccessException e) {
            //삭제하려는 게시글이 이미 존재하지 않을 때 나타냄
            check = false;
        }

        return check;
    }
}
