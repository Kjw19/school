package sm.school.dao.contest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.contest.ContestRepository;
import sm.school.domain.contest.Contest;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor

public class JpaContestDAO implements ContestDAO{
    private final ContestRepository contestRepository;
    @Override
    public Contest save(Contest contest) {
        return contestRepository.save(contest);
    }

    @Override
    public List<Contest> findAll() {
        return contestRepository.findAll();
    }

    @Override
    public Contest findContestById(Long id) {
        return contestRepository.findContestById(id);
    }

    @Override
    public Optional<Contest> findById(Long id) {
        return contestRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        contestRepository.deleteById(id);

    }

    @Override
    public List<Contest> findContestByMemberUserId(String userId) {
        return contestRepository.findContestByMemberUserId(userId);
    }

    @Override
    public Boolean existsById(Long id) {
        return contestRepository.existsById(id);
    }

    @Override
    public Boolean existsByConName(String name) {
        return existsByConName(name);
    }
}
