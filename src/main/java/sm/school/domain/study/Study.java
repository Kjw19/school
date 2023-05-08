package sm.school.domain.study;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;
import sm.school.dto.StudyDTO;

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

    @Column(name = "study_name" ,unique = true, nullable = false)
    private String name; // 스터디이름

    @Column(name = "study_content", nullable = false)
    private String content; // 스터디 설명

    @Column(nullable = false)
    @CreationTimestamp
    private Date date; //스터디 생성일자

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 방식
    @JoinColumn(name = "mem_id")
    private Member member; //스터디 생성한사람

    @Column(name = "study_reg_type", nullable = false)
    private int reg_type; // 가입 방식(즉시가입(0), 승인 후 가입(1))

    //스터디 생성
    @Builder
    public Study(Long id, String name, String content, Date date, Member member, int reg_type) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.member = member;
        this.reg_type = reg_type;
    }

    //DTO -> StudyDTO로 변경
    public StudyDTO toStudyDTO() {
        return StudyDTO.builder()
                .id(id)
                .name(name)
                .content(content)
                .date(date)
                .member(member)
                .reg_type(reg_type)
                .build();
    }

    //스터디 정보 수정
    public void ModifyStudy(String name, String content, int reg_type) {
        this.name = name;
        this.content = content;
        this.reg_type = reg_type;
    }
}
