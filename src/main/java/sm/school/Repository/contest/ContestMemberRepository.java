package sm.school.Repository.contest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.contest.ContestMember;

import java.util.List;

@Repository
public interface ContestMemberRepository extends JpaRepository<ContestMember,Long> {
    ContestMember findContestMemberById(Long id);

    List<ContestMember> findByContestId(Long id);
}
