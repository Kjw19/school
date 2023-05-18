package sm.school.dao.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.MeetingRepository;
import sm.school.domain.meeting.Meeting;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMeetingDao implements MeetingDao{

    private final MeetingRepository meetingRepository;

    @Override
    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public Optional<Meeting> findById(Long id) {
        return meetingRepository.findById(id);
    }

    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @Override
    public Meeting findMeetingById(Long id) {
        return meetingRepository.findMeetingById(id);
    }

    @Override
    public void deleteById(Long id) {
        meetingRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return meetingRepository.existsById(id);
    }

    @Override
    public List<Meeting> findMeetingByMemberUserId(String userId) {
        return meetingRepository.findMeetingByMemberUserId(userId);
    }

}
