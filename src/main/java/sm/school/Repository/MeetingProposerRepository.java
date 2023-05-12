package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.meeting.MeetingProposer;

import java.util.List;

public interface MeetingProposerRepository extends JpaRepository<MeetingProposer, Long> {



    List<MeetingProposer> findByMeetingsId(Long id);
}
