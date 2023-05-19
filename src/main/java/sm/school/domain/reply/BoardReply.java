package sm.school.domain.reply;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;
import sm.school.dto.reply.BoardReplyDTO;

import javax.persistence.*;

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
    public BoardReply(String content, Member member, Long id, Board board) {
        super(content, member);
        this.id = id;
        this.board = board;
    }

    public BoardReplyDTO toBoardReplyDTO() {
        return BoardReplyDTO.builder()
                .content(builder().content)
                .member(builder().member)
                .id(id)
                .board(board)
                .build();
    }


}
