package sm.school.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;
import sm.school.domain.reply.BoardReply;

@Getter
@Setter
@NoArgsConstructor
public class BoardReplyDTO extends ReplyDTO{

    private Long id; //게시글 댓글 기본값

    private Board board; //게시글 id

    @Builder
    public BoardReplyDTO(String content, Member member, String picture, Long id, Board board) {
        super(content, member,picture);
        this.id = id;
        this.board = board;
    }

    public BoardReply toBoardReply() {
        return BoardReply.builder()
                .content(builder().content)
                .member(builder().member)
                .id(id)
                .board(board)
                .build();
    }
}
