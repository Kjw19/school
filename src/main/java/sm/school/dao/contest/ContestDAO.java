package sm.school.dao.contest;

import sm.school.domain.contest.Contest;
import sm.school.domain.study.Study;

import java.util.List;
import java.util.Optional;

public interface ContestDAO {
    Contest save(Contest contest);

    List<Contest> findAll();

    Contest findContestById(Long id);

    Optional<Contest> findById(Long id);

    void deleteById(Long id);

    List<Contest> findContestByMemberUserId(String userId);

    Boolean existsById(Long id);

    Boolean existsByConName(String name);


}
