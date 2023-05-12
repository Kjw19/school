package sm.school.dto.meeting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.member.Member;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDTO {

    private Long id;

    @NotEmpty(message = "미팅 파티 이름을 입력하세요")
    private String title;

    @NotEmpty(message = "소개 글을 간략하게 입력하세요")
    private String introduction;

    @NotEmpty(message = "학교를 입력하세요")
    private String school; //학교

    @NotEmpty(message = "전공을 입력하세요")
    private String major; // 전공

    @NotEmpty(message = "지역을 입력하세요")
    private String region; //지역

    @NotNull(message = "미팅인원을 입력하세요")
    @Min(value = 2, message = "2명이상 이어야 합니다.")
    @Max(value = 6, message = "최대 6명까지 가능합니다.")
    private int count = 2; //미팅인원

    private int status = 0;

    private Date date;

    private Member member;//미팅 생성 회원

    @Builder
    public MeetingDTO(Long id, String title, String introduction, String school,
                      String major, String region, int count, int status, Date date,Member member) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
        this.status = status;
        this.date = date;
        this.member = member;
    }

    public Meeting toMeetingEntity() {
        return Meeting.builder()
                .id(id)
                .title(title)
                .introduction(introduction)
                .school(school)
                .major(major)
                .region(region)
                .count(count)
                .status(status)
                .date(date)
                .member(member)
                .build();
    }

}
