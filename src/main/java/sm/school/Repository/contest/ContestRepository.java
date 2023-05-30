package sm.school.Repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.contest.Contest;
import sm.school.domain.study.Study;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest,Long> {
    Contest findContestById(Long id);

    Boolean existsByConName(String name);

    List<Contest> findContestByMemberUserId(String userId);




}
