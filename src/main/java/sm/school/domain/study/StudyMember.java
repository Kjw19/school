package sm.school.domain.study;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;
import sm.school.dto.StudyMemberDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_mem_id")
    private Long id;//기본값

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 방식
    @JoinColumn(name = "study_id")
    private Study study; //스터디 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member; //스터디 가입한 사람

    @Column(name = "study_reg_date")
    @CreationTimestamp
    private Date date; // 가입일자

    @Column(name = "study_mem_role", nullable = false)
    private int role; // 권한 (0: 대기회원, 1: 일반회원)

    //스터디 회원 가입
    @Builder
    public StudyMember(Long id, Study study, Member member, Date date, int role) {
        this.id = id;
        this.study = study;
        this.member = member;
        this.date = date;
        this.role = role;
    }

    //StudyMember -> StudyMemberDTO
    public StudyMemberDTO toStudyMemberDTO() {
        return StudyMemberDTO.builder()
                .id(id)
                .study(study)
                .member(member)
                .date(date)
                .role(role)
                .build();
    }

    //스터디 회원 권한 수정
    public void ModifyStudyRole(int role) {
        this.role = role;
    }
}
