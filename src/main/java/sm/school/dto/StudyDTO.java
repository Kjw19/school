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

    @NotEmpty(message = "스터디 지역을 작성해 주세요.")
    private String region; //스터디 지역

    private int status = 0; //현재 모집중인지 상태 (0: 모집중, 1: 마감)


    private Date date; //스터디 생성일자


    private Member member; //스터디 생성한사람


    private int regType = 0; // 가입 방식(즉시가입(0), 승인 후 가입(1))

    @Builder
    public StudyDTO(Long id, String name, String content, String region,
                    int status, Date date, Member member, int regType) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.region = region;
        this.status = status;
        this.date = date;
        this.member = member;
        this.regType = regType;
    }

    public Study toStudyEntity() {
        return Study.builder()
                .id(id)
                .name(name)
                .content(content)
                .region(region)
                .status(status)
                .date(date)
                .member(member)
                .regType(regType).build();
    }
}
