package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.meeting.MeetingProposer;

@Repository
public interface MeetingProposerRepository extends JpaRepository<MeetingProposer, Long> {


    MeetingProposer findMeetingProposerById(Long id);
}
