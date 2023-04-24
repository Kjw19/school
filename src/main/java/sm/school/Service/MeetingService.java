package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.school.Repository.MeetingRepository;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.meeting.Meeting;
import sm.school.dto.MeetingDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public Meeting createMeeting(MeetingDTO meetingDTO) {

        Meeting meeting = meetingDTO.toMeetingEntity();

        return meetingRepository.save(meeting);

    }

    public List<Meeting> findMeeting() {

        List<Meeting> meetingList = meetingRepository.findAll();


        return meetingList;
    }
}
