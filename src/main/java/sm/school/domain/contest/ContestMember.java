package sm.school.domain.contest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContestMember {
    @Id
    @GeneratedValue
    private Long id;//기본값
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;//대회 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;//대회 참가한 사람
    @Column(name = "contest_reg_date")
    @CreationTimestamp
    private Date date;//참여 날짜
    @Column(name = "contest_reg_type")
    @ColumnDefault("1")//1.기본회원 2.차단회원
    private int reg_type;//권한
    @Builder
    public ContestMember(Long id, Contest contest, Member member, Date date, int reg_type) {
        this.id = id;
        this.contest = contest;
        this.member = member;
        this.date = date;
        this.reg_type = reg_type;
    }
    public void Modify_Conmem(int reg_type){
        this.reg_type=reg_type;
    }
}
