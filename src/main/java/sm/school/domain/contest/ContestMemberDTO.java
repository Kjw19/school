package sm.school.domain.contest;

import lombok.*;
import sm.school.domain.member.Member;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ContestMemberDTO {
    private Long id;//기본값

    private Contest contest;//대회 번호

    private Member member;//대회 참가한 사람

    private Date date;//참여 날짜

    private int role=0;//권한
    @Builder
    public ContestMemberDTO(Long id, Contest contest, Member member, Date date, int role) {
        this.id = id;
        this.contest = contest;
        this.member = member;
        this.date = date;
        this.role = role;
    }
    public ContestMember toContestMemberEntity(){
        return ContestMember.builder()
                .id(id)
                .contest(contest)
                .member(member)
                .date(date)
                .role(role)
                .build();

    }
}
