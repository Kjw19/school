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
    @Column(name = "con_mem_id")
    private Long id;//기본값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;//대회 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;//대회 참가한 사람

    @Column(name = "contest_date")
    @CreationTimestamp
    private Date date;//참여 날짜

    @Column(name = "contest_mem_type")
    @ColumnDefault("1")//1.기본회원 2.차단회원 3.방장
    private Integer regType;//권한

    @Builder
    public ContestMember(Long id, Contest contest, Member member, Date date, Integer regType) {
        this.id = id;
        this.contest = contest;
        this.member = member;
        this.date = date;
        this.regType = regType;
    }
    public ContestMemberDTO toContestMemberDTO(){
        return ContestMemberDTO.builder()
                .id(id)
                .contest(contest)
                .member(member)
                .date(date)
                .regType(regType)
                .build();
    }
    public void Modify_Conmem(Integer regType){
        this.regType=regType;
    }
}
