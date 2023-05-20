package sm.school.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sm.school.domain.reply.BoardReply;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    BoardReply findBoardReplyById(Long id);
    List<BoardReply> findBoardRepliesByBoardId(Long id);

    List<BoardReply> findBoardRepliesByMemberUserId(String userId);

}
