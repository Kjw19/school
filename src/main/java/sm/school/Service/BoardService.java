package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.dao.board.JpaBoardDao;
import sm.school.domain.board.Board;
import sm.school.dto.BoardDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final JpaBoardDao jpaBoardDao;

    private final CommonService commonService;


    public Board createBoard(BoardDTO boardDTO, Authentication authentication) {
        boardDTO.setMember(commonService.getMemberFromAuthentication(authentication));
        Board board = boardDTO.toBoard();
        return jpaBoardDao.save(board);
    }

    public void updateBoard(BoardDTO boardDTO) {
        Board board = jpaBoardDao.findBoardById(boardDTO.getId());
        board.updateBoard(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getPicture());
    }

    @Transactional(readOnly = true)
    public List<Board> findBoard() {
        List<Board> boardList = jpaBoardDao.findAll();

        return boardList;
    }

    @Transactional(readOnly = true)
    public BoardDTO selectBoard(Long id) {

        Board board = jpaBoardDao.findBoardById(id);
        BoardDTO boardDTO = board.toBoardDTO();
        return boardDTO;
    }

    public void deleteBoard(Long id) {

        if (!jpaBoardDao.existsById(id)) {
            throw new DataNotFoundException();
        }
        jpaBoardDao.deleteById(id);
    }
}

