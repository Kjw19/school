package sm.school.dao.board;

import sm.school.domain.reply.BoardReply;

import java.util.List;
import java.util.Optional;

public interface BoardReplyDao {

    BoardReply save(BoardReply boardReply);

    Optional<BoardReply> findById(Long id);

    void deleteById(Long id);

    BoardReply findBoardReplyById(Long id);

    List<BoardReply> findBoardRepliesByBoardId(Long id);

    List<BoardReply> findBoardRepliesByMemberUserId(String userId);

}
