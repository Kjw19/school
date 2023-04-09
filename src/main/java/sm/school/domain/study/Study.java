package sm.school.domain.study;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.One;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;//기본값

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    @Column(unique = true)
    private String study_name; // 스터디이름

    @NotEmpty
    private String study_content; // 스터디 설명

    @NotEmpty
    @Column(name = "create_study_date", columnDefinition = "DEFAULT SYSDATE")
    private Date date; //스터디 생성일자

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 방식
    @JoinColumn(name = "mem_id")
    private Member member; //스터디 생성한사람

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    @Column(name = "study_reg_type", columnDefinition = "INT DEFAULT 0")
    private int reg_type; // 가입 방식(즉시가입(0), 승인 후 가입(1))

    //스터디 생성
    @Builder
    public Study(Long id, String study_name, String study_content, Date date, Member member, int reg_type) {
        this.id = id;
        this.study_name = study_name;
        this.study_content = study_content;
        this.date = date;
        this.member = member;
        this.reg_type = reg_type;
    }

    //스터디 정보 수정
    public void ModifyStudy(String study_name, String study_content, int reg_type) {
        this.study_name = study_name;
        this.study_content = study_content;
        this.reg_type = reg_type;
    }
}
