package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.commonConst.DefaultProfileUrl;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.FileSizeException;
import sm.school.dao.board.JpaBoardDao;
import sm.school.domain.board.Board;
import sm.school.dto.BoardDTO;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {

    private final JpaBoardDao jpaBoardDao;

    private final CommonService commonService;

    public Board createBoard(BoardDTO boardDTO, MultipartFile imageFile, Authentication authentication) {


        updateImage(boardDTO, imageFile);

        boardDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Board board = boardDTO.toBoard();
        return jpaBoardDao.save(board);
    }

    public void updateBoard(BoardDTO boardDTO, MultipartFile imageFile) {

        Board board = jpaBoardDao.findBoardById(boardDTO.getId());
        boardDTO.setPicture(board.getPicture());
        updateImage(boardDTO, imageFile);
        board.updateBoard(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getPicture());
    }

    @Transactional(readOnly = true)
    public List<BoardDTO> findBoard() {
        return jpaBoardDao.findAll().stream()
                .map(Board::toBoardDTO)
                .collect(Collectors.toList());
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

    public void updateImage(BoardDTO boardDTO, MultipartFile multipartFile) {
        String upload = commonService.processUpload(multipartFile);
        if (upload != null) {
            boardDTO.setPicture(upload);
        }
    }

    public String currentImage(Long id) {

        Board board = jpaBoardDao.findBoardById(id);
        BoardDTO boardDTO = board.toBoardDTO();

        return boardDTO.getPicture();
    }
}

