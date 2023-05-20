package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.dao.board.BoardReplyDao;
import sm.school.dao.board.JpaBoardReplyDao;
import sm.school.domain.reply.BoardReply;
import sm.school.dto.BoardDTO;
import sm.school.dto.reply.BoardReplyDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardReplyService {

    private final JpaBoardReplyDao jpaBoardReplyDao;
    private final CommonService commonService;

    //댓글
    public BoardReply createBoard(BoardReplyDTO boardReplyDTO, MultipartFile imageFile, Authentication authentication) {

        updateImage(boardReplyDTO, imageFile);

        boardReplyDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        BoardReply boardReply = boardReplyDTO.toBoardReply();

        return jpaBoardReplyDao.save(boardReply);
    }


    @Transactional(readOnly = true)
    public List<BoardReplyDTO> findReplyByBoardId(Long boardId) {
        return jpaBoardReplyDao.findBoardRepliesByBoardId(boardId).stream()
                .map(BoardReply::toBoardReplyDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardReplyDTO SelectBoardReply(Long replyId) {
        BoardReply boardReply = jpaBoardReplyDao.findBoardReplyById(replyId);
        BoardReplyDTO boardReplyDTO = boardReply.toBoardReplyDTO();

        return boardReplyDTO;
    }

    //사진 업로드
    public void updateImage(BoardReplyDTO boardReplyDTO, MultipartFile multipartFile) {
        String upload = commonService.processUpload(multipartFile);
        if (upload != null) {
            boardReplyDTO.setPicture(upload);
        }
    }
}
