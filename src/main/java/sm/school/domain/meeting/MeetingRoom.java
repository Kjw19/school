package sm.school.domain.meeting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoom {

    @Id//기본값
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "meet_id") //미팅 참여 팀
    private List<Meeting> meetings = new ArrayList<>();

    //방 생성
    @Builder
    public MeetingRoom(Long id, List<Meeting> meetings) {
        this.id = id;
        this.meetings = meetings;
    }

    //미팅 팀 수정
    public void ModifyMeetingRoom(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}


