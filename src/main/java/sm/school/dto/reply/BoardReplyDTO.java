package sm.school.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;
import sm.school.domain.reply.BoardReply;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardReplyDTO extends ReplyDTO{

    private Long id; //게시글 댓글 기본값

    private Board board; //게시글 id

    @Builder
    public BoardReplyDTO(String content, Member member, String picture, Date date, Date modify_date, Long id, Board board) {
        super(content, member,picture, date, modify_date);
        this.id = id;
        this.board = board;
    }

    public BoardReply toBoardReply() {
        return BoardReply.builder()
                .content(this.getContent())
                .member(this.getMember())
                .picture(this.getPicture())
                .date(this.getDate())
                .modify_date(this.getModify_date())
                .id(id)
                .board(board)
                .build();
    }
}
