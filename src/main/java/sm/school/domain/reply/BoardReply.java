package sm.school.domain.reply;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;
import sm.school.dto.reply.BoardReplyDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReply extends Reply {

    @Id
    @GeneratedValue
    @Column(name = "board_reply_id")
    private Long id; //게시글 댓글 기본값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; //게시글 id

    @Builder
    public BoardReply(String content, Member member, String picture, Date date, Date modify_date, Long id, Board board) {
        super(content, member,picture, date, modify_date);
        this.id = id;
        this.board = board;
    }

    public BoardReplyDTO toBoardReplyDTO() {
        return BoardReplyDTO.builder()
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
