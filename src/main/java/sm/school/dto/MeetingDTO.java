package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.member.Member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDTO {

    private Long id;

    @NotEmpty//해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String school; //학교

    @NotEmpty
    private String major; // 전공

    @NotEmpty
    private String region; //지역

    @NotNull
    private int count; //미팅인원

    private Member member;//미팅 생성 회원

    @Builder
    public MeetingDTO(Long id, String school, String major, String region, int count, Member member) {
        this.id = id;
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
        this.member = member;
    }

    public Meeting toMeetingEntity() {
        return Meeting.builder()
                .id(id)
                .school(school)
                .major(major)
                .region(region)
                .count(count)
                .member(member)
                .build();
    }
}
