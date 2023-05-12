package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.school.domain.meeting.Meeting;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findMeetingById(Long id);

}
