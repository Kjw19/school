package sm.school.domain.study;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;

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

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    @Column(name = "study_mem_role")
    @ColumnDefault("1") // 1:기본 회원, 2: 차단 회원
    private int role; // 권한

    //스터디 회원 가입
    @Builder
    public StudyMember(Long id, Study study, Member member, Date date, int role) {
        this.id = id;
        this.study = study;
        this.member = member;
        this.date = date;
        this.role = role;
    }

    //스터디 회원 권한 수정
    public void ModifyStudyRole(int role) {
        this.role = role;
    }
}
