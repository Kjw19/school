package sm.school.domain.contest;

import lombok.*;
import sm.school.domain.member.Member;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContestMemberDTO {
    private Long id;//기본값

    private Contest contest;//대회 번호

    private Member member;//대회 참가한 사람

    private Date date;//참여 날짜

    private Integer regType;//권한
    @Builder
    public ContestMemberDTO(Long id, Contest contest, Member member, Date date, Integer regType) {
        this.id = id;
        this.contest = contest;
        this.member = member;
        this.date = date;
        this.regType = regType;
    }
    public ContestMember toContestMemberEntity(){
        return ContestMember.builder()
                .id(id)
                .contest(contest)
                .member(member)
                .date(date)
                .regType(regType)
                .build();

    }
}
