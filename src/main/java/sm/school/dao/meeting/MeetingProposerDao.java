package sm.school.dao.meeting;

import sm.school.domain.meeting.MeetingProposer;

import java.util.List;
import java.util.Optional;

public interface MeetingProposerDao {

    List<MeetingProposer> findAll();

    Optional<MeetingProposer> findById(Long id);

    MeetingProposer save(MeetingProposer meetingProposer);

    List<MeetingProposer> findByMeetingsId(Long id);

    void deleteById(Long id);
}
