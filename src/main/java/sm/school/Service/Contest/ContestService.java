package sm.school.Service.Contest;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.contest.ContestRepository;
import sm.school.domain.contest.Contest;
import sm.school.domain.contest.ContestDTO;

import java.util.List;

@Service
@RequiredArgsConstructor//생성자 주입 autowire 보다 장점이 많다(final 사용가능)
@Transactional

public class ContestService {
    private final ContestRepository contestRepository;


    public Contest creatContest(ContestDTO contestDTO){
        Contest contest = contestDTO.toContestEntity();//DTO->entity로 변환


        return contestRepository.save(contest);
    }
    @Transactional(readOnly = true)
    public List<Contest>ListContest(){
        return contestRepository.findAll();
    }
    @Transactional(readOnly = true)
    public ContestDTO selectContest(Long id){
        Contest contestById = contestRepository.findContestById(id);
        ContestDTO contestDTO = contestById.toContestDTO();
        return contestDTO;

    }
    public void updateContest(ContestDTO contestDTO){
        Contest contest = contestRepository.findContestById(contestDTO.getId());
        contest.ModifyContest(contest.getConName(), contest.getConInf(), contest.getConPicture(),contest.getRegType());
    }
    public Boolean deleteContest(Long id) {
        try {
            contestRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            //삭제하려는 게시글이 이미 존재하지 않을 때 나타냄
            return false;
        }
    }


}
