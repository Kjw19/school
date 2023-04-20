package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.meeting.MeetingRoom;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeetingRoomDTO {


    @NotEmpty
    private Long id;

    @NotEmpty
    private Date date;

    @NotEmpty
    private List<Meeting> meetings = new ArrayList<>();


    @Builder
    public MeetingRoomDTO(Long id, Date date, List<Meeting> meetings) {
        this.id = id;
        this.date = date;
        this.meetings = meetings;
    }

    //DTO -> Entity 변환
    public MeetingRoom toEntity() {
        return MeetingRoom.builder()
                .id(id)
                .date(date)
                .meetings(meetings)
                .build();
    }
}



