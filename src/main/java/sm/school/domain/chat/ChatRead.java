package sm.school.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.member.Member;

import javax.persistence.*;

//@Getter
//@Entity

public abstract class ChatRead {
    @Id
    @GeneratedValue
    @Column(name = "chatRead_id")
    private Long id;//챗 리드 번호

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "chatMsg_id")
    private ChatMessage chatMessage;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
