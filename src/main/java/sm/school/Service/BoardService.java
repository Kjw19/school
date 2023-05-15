package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.dao.board.JpaBoardDao;
import sm.school.domain.board.Board;
import sm.school.dto.BoardDTO;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final JpaBoardDao jpaBoardDao;

    private final CommonService commonService;

    private final S3Client s3Client;

    public Board createBoard(BoardDTO boardDTO, MultipartFile imageFile, Authentication authentication) {

        if (!imageFile.isEmpty()) {
            if (imageFile.getSize() > 5000000) {
                String profileImageUrl = commonService.uploadFileToS3(imageFile);
                boardDTO.setPicture(profileImageUrl);
            }
        }

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

