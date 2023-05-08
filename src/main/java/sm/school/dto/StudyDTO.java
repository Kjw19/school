package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;
import sm.school.domain.study.Study;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StudyDTO {

    private Long id;//기본값

    @NotEmpty(message = "스터디 명을 입력해주세요")
    private String name; // 스터디이름


    @NotEmpty(message = "스터디에 대한 설명을 입력해주세요")
    private String content; // 스터디 설명

    private Date date; //스터디 생성일자


    private Member member; //스터디 생성한사람


    private int reg_type = 0; // 가입 방식(즉시가입(0), 승인 후 가입(1))


    @Builder
    public StudyDTO(Long id, String name, String content, Date date, Member member, int reg_type) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.member = member;
        this.reg_type = reg_type;
    }

    public Study toStudyEntity() {
        return Study.builder()
                .id(id)
                .name(name)
                .content(content)
                .date(date)
                .member(member)
                .reg_type(reg_type).build();
    }
}
