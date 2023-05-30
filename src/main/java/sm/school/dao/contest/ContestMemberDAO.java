package sm.school.dao.contest;

import sm.school.domain.contest.ContestMember;
import sm.school.domain.study.StudyMember;

import java.util.List;
import java.util.Optional;

public interface ContestMemberDAO {
    List<ContestMember> findAll();

    ContestMember save(ContestMember contestMember);

    Optional<ContestMember> findById(Long id);

    ContestMember findContestMemberById(Long id);

    List<ContestMember> findByContestId(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);
}
