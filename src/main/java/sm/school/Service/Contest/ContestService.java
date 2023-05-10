package sm.school.Service.Contest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.contest.ContestMemberRepository;
import sm.school.Repository.contest.ContestRepository;
import sm.school.domain.contest.Contest;
import sm.school.domain.contest.ContestDTO;
import sm.school.domain.contest.ContestMember;
import sm.school.domain.study.StudyMember;

import java.util.List;

@Service
@RequiredArgsConstructor//생성자 주입 autowire 보다 장점이 많다(final 사용가능)
@Transactional
@Slf4j

public class ContestService {
    private final ContestRepository contestRepository;

    private final ContestMemberRepository contestMemberRepository;


    public Contest creatContest(ContestDTO contestDTO){
        Contest contest = contestDTO.toContestEntity();//DTO->entity로 변환

        log.info("conName {}",contestDTO.getConName());
        log.info("conInf {}",contestDTO.getConInf());
        log.info("conRegType{}",contestDTO.getRegType());

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
        contest.ModifyContest(contestDTO.getConName(), contestDTO.getConInf(), contestDTO.getConPicture(),contestDTO.getRegType());

        log.info("conName {}",contest.getConName());
        log.info("conInf {}",contest.getConInf());
        log.info("conRegType {}",contest.getRegType());


    }


    public Boolean deleteContest(Long id) {
        try {
            List<ContestMember> byContestId = contestMemberRepository.findByContestId(id);

            for (ContestMember contestMember : byContestId) { //스터디에 포함되어 있는 스터디 회원 삭제
                contestMemberRepository.deleteById(contestMember.getId());
            }
            contestRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }


}
