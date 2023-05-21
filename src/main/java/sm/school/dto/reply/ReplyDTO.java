package sm.school.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;


import javax.validation.constraints.NotEmpty;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public abstract class ReplyDTO {


    @NotEmpty(message = "댓글 내용을 작성해 주세요")
    private String content; //댓글 내용

    private Member member; //작성자

    private String picture;

    private Date date; // 작성 시간

    private Date modify_date; //수정 시간

    protected ReplyDTO(String content, Member member, String picture) {
        this.content = content;
        this.member = member;
        this.picture = picture;
    }
}
