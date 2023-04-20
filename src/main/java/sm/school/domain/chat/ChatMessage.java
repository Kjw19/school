package sm.school.domain.chat;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter

public abstract class ChatMessage {

    @Id
    @GeneratedValue
    @Column(name="chatMsg_id")
    private Long id;//메지시 번호

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;//수신 그룹

    @ManyToOne
    @JoinColumn(name = "Member_id")
    private Member member;//발신자

    @NotNull
    private String content;//메시지 내용

    @NotEmpty
    @CreatedDate
    @Column(name = "message_reg_date")
    private Date date;//메시지 발송 날짜





}

