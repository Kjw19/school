package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;
import sm.school.domain.study.Study;
import sm.school.domain.study.StudyMember;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StudyMemberDTO {


    private Long id;//기본값

    private Study study; //스터디 번호

    private Member member; //스터디 가입한 사람

    private Date date; // 가입일자

    private int role; // 권한 (0: 대기회원, 1: 일반회원)

    @Builder
    public StudyMemberDTO(Long id, Study study, Member member, Date date, int role) {
        this.id = id;
        this.study = study;
        this.member = member;
        this.date = date;
        this.role = role;
    }

    //StudyMemberDTO -> StudyMember
    public StudyMember toStudyMember() {
        return StudyMember.builder()
                .id(id)
                .member(member)
                .study(study)
                .date(date)
                .role(role)
                .build();
    }
}
