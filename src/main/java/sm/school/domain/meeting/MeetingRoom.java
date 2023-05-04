package sm.school.domain.meeting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoom {

    @Id//기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "create_meeting")
    @CreationTimestamp
    private Date date;

    @OneToMany
    @JoinColumn(name = "meet_id") //미팅 참여 팀
    private List<Meeting> meetings = new ArrayList<>();

    //방 생성


    @Builder
    public MeetingRoom(Long id, Date date, List<Meeting> meetings) {
        this.id = id;
        this.date = date;
        this.meetings = meetings;
    }

    //미팅 팀 수정
    public void ModifyMeetingRoom(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}


