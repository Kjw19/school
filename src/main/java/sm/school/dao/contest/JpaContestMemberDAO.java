package sm.school.dao.contest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.contest.ContestMemberRepository;
import sm.school.domain.contest.ContestMember;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class JpaContestMemberDAO implements ContestMemberDAO{

    private final ContestMemberRepository contestMemberRepository;
    @Override
    public List<ContestMember> findAll() {
        return contestMemberRepository.findAll();
    }

    @Override
    public ContestMember save(ContestMember contestMember) {
        return contestMemberRepository.save(contestMember);
    }

    @Override
    public Optional<ContestMember> findById(Long id) {
        return contestMemberRepository.findById(id);
    }

    @Override
    public ContestMember findContestMemberById(Long id) {
        return contestMemberRepository.findContestMemberById(id);
    }

    @Override
    public List<ContestMember> findByContestId(Long id) {
        return contestMemberRepository.findByContestId(id);
    }

    @Override
    public void deleteById(Long id) {
        contestMemberRepository.deleteById(id);

    }

    @Override
    public Boolean existsById(Long id) {
        return contestMemberRepository.existsById(id);
    }
}
