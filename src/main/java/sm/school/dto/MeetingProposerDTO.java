package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.meeting.MeetingProposer;
import sm.school.domain.member.Member;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MeetingProposerDTO {

    private Long id;

    private String introduction; //소개 글

    private String school; //학교

    private String major; // 전공

    private String region; //지역

    private int count; //미팅인원

    private int status = 0; //0이면 신청대기 1이면 매칭성공

    private Date date;

    private Meeting meetings;

    private Member member;


    @Builder
    public MeetingProposerDTO(Long id, String introduction, String school, String major,
                              String region, int count, int status, Date date,
                              Meeting meetings, Member member) {
        this.id = id;
        this.introduction = introduction;
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
        this.status = status;
        this.date = date;
        this.meetings = meetings;
        this.member = member;
    }

    public MeetingProposer toMeetingProposer() {
        return MeetingProposer.builder()
                .id(id)
                .introduction(introduction)
                .school(school)
                .major(major)
                .region(region)
                .count(count)
                .status(status)
                .date(date)
                .meetings(meetings)
                .member(member)
                .build();
    }

}
