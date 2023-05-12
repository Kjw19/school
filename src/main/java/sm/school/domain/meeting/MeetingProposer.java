package sm.school.domain.meeting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;
import sm.school.dto.meeting.MeetingProposerDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingProposer {

    @Id//기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String introduction; //소개 글

    @Column(nullable = false)
    private String school; //학교

    @Column(nullable = false)
    private String major; // 전공

    @Column(nullable = false)
    private String region; //지역

    @Column(nullable = false)
    private int count; //미팅인원

    @Column(nullable = false)
    private int status;

    @Column(name = "create_meeting")
    @CreationTimestamp
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_id") //참여하는 미팅
    private Meeting meetings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;


    @Builder
    public MeetingProposer(Long id, String introduction, String school, String major,
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

    public MeetingProposerDTO toMeetingProposerDTO() {
        return MeetingProposerDTO.builder()
                .id(id)
                .introduction(introduction)
                .school(school)
                .major(major)
                .region(region)
                .count(count)
                .date(date)
                .status(status)
                .meetings(meetings)
                .member(member)
                .build();
    }

    public void changeStatus(int status) {
        this.status = status;
    }
}


