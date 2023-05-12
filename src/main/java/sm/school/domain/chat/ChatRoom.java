package sm.school.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

//@Entity
@Getter

public abstract class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;//채팅방 번호

    @NotBlank
    private String chat_Name;//채팅 이름

    @NotEmpty
    @CreatedDate
    @Column(name = "chat_reg_date")
    private Date date;//생성 날짜



}
