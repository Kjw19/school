package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.MeetingRepository;
import sm.school.domain.meeting.Meeting;
import sm.school.dto.MeetingDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public Meeting createMeeting(MeetingDTO meetingDTO) {

        Meeting meeting = meetingDTO.toMeetingEntity();

        return meetingRepository.save(meeting);

    }

    public void updateMeeting(MeetingDTO meetingDTO) {

        Meeting meeting = meetingRepository.findMeetingById(meetingDTO.getId());

        meeting.modifyMeeting(meetingDTO.getTitle(), meetingDTO.getIntroduction(),
                meetingDTO.getSchool(), meetingDTO.getMajor(), meetingDTO.getRegion(),
                meetingDTO.getCount());
    }

    public List<Meeting> findMeeting() {

        List<Meeting> meetingList = meetingRepository.findAll();


        return meetingList;
    }

    public MeetingDTO selectMeeting(Long id) {

        Meeting meeting = meetingRepository.findMeetingById(id);

        MeetingDTO meetingDTO = meeting.toMeetingDTO();

        return meetingDTO;
    }

    public Boolean DeleteMeeting(Long id) {

        try {
            meetingRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            //삭제하려는 미팅이 이미 존재하지 않을 때
            return false;
        }
    }
}
