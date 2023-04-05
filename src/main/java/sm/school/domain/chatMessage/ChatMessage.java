package sm.school.domain.chatMessage;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public abstract class ChatMessage {

    @Id
    @GeneratedValue
    @Column(name="chatMsg_id")
    private Long id;

    private String content;

}

