package sm.school.domain.reply;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sm.school.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

//댓글 추상체
@Getter
@Setter
@NoArgsConstructor
public abstract class Reply {

    @Column(columnDefinition = "TEXT",  nullable = false)
    private String content; //댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member; //작성자

    @CreationTimestamp
    @Column(name = "reply_date")
    private Date date; // 작성 시간

    @UpdateTimestamp
    @Column(name = "modify_reply_date")
    private Date modify_date; //수정 시간

    protected Reply(String content, Member member) {
        this.content = content;
        this.member = member;
    }
}
