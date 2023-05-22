package sm.school.dao.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.BoardReplyRepository;
import sm.school.domain.reply.BoardReply;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBoardReplyDao implements BoardReplyDao{

    private final BoardReplyRepository boardReplyRepository;

    @Override
    public BoardReply save(BoardReply boardReply) {
        return boardReplyRepository.save(boardReply);
    }

    @Override
    public Optional<BoardReply> findById(Long id) {
        return boardReplyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        boardReplyRepository.deleteById(id);
    }

    @Override
    public BoardReply findBoardReplyById(Long id) {
        return boardReplyRepository.findBoardReplyById(id);
    }

    @Override
    public List<BoardReply> findBoardRepliesByBoardId(Long id) {
        return boardReplyRepository.findBoardRepliesByBoardId(id);
    }

    @Override
    public List<BoardReply> findBoardRepliesByMemberUserId(String userId) {
        return boardReplyRepository.findBoardReplyByMemberUserId(userId);
    }

    @Override
    public Boolean existsById(Long id) {
        return boardReplyRepository.existsById(id);
    }
}
