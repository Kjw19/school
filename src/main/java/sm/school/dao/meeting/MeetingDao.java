package sm.school.dao.meeting;

import sm.school.domain.meeting.Meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingDao {
    List<Meeting> findAll();

    Meeting save(Meeting meeting);

    Optional<Meeting> findById(Long id);

    Meeting findMeetingById(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);

    List<Meeting> findMeetingByMemberUserId(String userId);
}
