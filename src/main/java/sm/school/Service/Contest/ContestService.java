package sm.school.Service.Contest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sm.school.Repository.contest.ContestRepository;
import sm.school.domain.contest.Contest;
import sm.school.domain.contest.ContestDTO;
@Service
@RequiredArgsConstructor//생성자 주입 autowire 보다 장점이 많다(final 사용가능)

public class ContestService {
    private final ContestRepository contestRepository;

    public Contest creatContest(ContestDTO contestDTO){
        Contest contest = contestDTO.toContestEntity();//DTO->entity로 변환

        return contestRepository.save(contest);
    }


}
