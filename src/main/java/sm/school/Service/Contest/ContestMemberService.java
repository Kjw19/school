package sm.school.Service.Contest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.contest.ContestMemberRepository;
import sm.school.domain.contest.ContestMember;
import sm.school.domain.contest.ContestMemberDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContestMemberService {
    private final ContestMemberRepository contestMemberRepository;

    public ContestMember saveConMember(ContestMemberDTO contestMemberDTO){
        ContestMember contestMember = contestMemberDTO.toContestMemberEntity();
        return contestMemberRepository.save(contestMember);

    }
    public List<ContestMember> findContestMemberByConId(Long id){
        return contestMemberRepository.findByContestId(id);
    }

    public List<ContestMemberDTO> selectContestMember(Long id) {//컨테스트 멤버 불러오기

        List<ContestMember> byContestId = contestMemberRepository.findByContestId(id);
        List<ContestMemberDTO> ContestMemberDTOList = new ArrayList<>();

        for (ContestMember contestMember: byContestId) {
            ContestMemberDTOList.add(contestMember.toContestMemberDTO());
        }

        return ContestMemberDTOList;
    }
}
