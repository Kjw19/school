package sm.school.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.member.Member;

import javax.persistence.*;

@Entity
@Getter

public abstract class ChatMember {
    @Id
    @GeneratedValue
    @Column(name = "chat_Mem_id")
    private Long id;//채팅 멤버 번호

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;//채팅방

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;//채팅 생성자



}
