package sm.school.Repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.contest.Contest;
@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {

}
