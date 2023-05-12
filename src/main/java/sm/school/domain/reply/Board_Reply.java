package sm.school.domain.reply;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.board.Board;

import javax.persistence.*;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board_Reply extends Reply {

    @Id
    @GeneratedValue
    @Column(name = "board_reply_id")
    private Long id; //게시글 댓글 기본값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; //게시글 id
}
