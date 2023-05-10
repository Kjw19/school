package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.MeetingProposerRepository;
import sm.school.Repository.MeetingRepository;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.meeting.MeetingProposer;
import sm.school.dto.MeetingDTO;
import sm.school.dto.MeetingProposerDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingProposerRepository meetingProposerRepository;

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
            //미팅 회원 목록 가져오기
            List<MeetingProposer> meetingProposerList = meetingProposerRepository.findByMeetingsId(id);

            //미팅 회원 목록 삭제
            for (MeetingProposer meetingProposer: meetingProposerList) {
                meetingProposerRepository.deleteById(meetingProposer.getId());
            }
            //미팅 삭제
            meetingRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            //삭제하려는 미팅이 이미 존재하지 않을 때
            return false;
        }
    }

    //미팅 완료상태 변경
    public void completeMeeting(Long meetId) {
        Meeting meeting = meetingRepository.findMeetingById(meetId);
        meeting.changeStatus(1);
    }
}
