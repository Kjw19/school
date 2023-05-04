package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.meeting.Meeting;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findMeetingById(Long id);

}
