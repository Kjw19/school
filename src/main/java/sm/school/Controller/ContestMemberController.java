package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.Contest.ContestMemberService;
import sm.school.Service.Contest.ContestService;
import sm.school.Service.MemberDetailsService;
import sm.school.domain.contest.ContestDTO;
import sm.school.domain.contest.ContestMemberDTO;
import sm.school.dto.MeetingDTO;
import sm.school.dto.MeetingProposerDTO;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/contestPro")
@RequiredArgsConstructor
public class ContestMemberController {
    private final ContestMemberService contestMemberService;
    private final ContestService contestService;

    @GetMapping("/create")
    public String createConMemFrom(@RequestParam("id") Long id, @ModelAttribute("contestMemberDTO")ContestMemberDTO contestMemberDTO,
                                   Authentication authentication){
        if (authentication==null){
            return "redirect:/member/login";
        }
        return "contest/memberCreate";
    }
    @PostMapping("/create")
    public String createMeetingProposer(@RequestParam("id") Long id, @Valid ContestMemberDTO contestMemberDTO,
                                        Authentication authentication) {
        //검증로직 시작
        if (authentication==null){
            return "redirect:/member/login";
        }
        //검증로직 끝

        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        contestMemberDTO.setMember(memberDetails.getMember());

        ContestDTO contestDTO = contestService.selectContest(id);//미팅의 pk

        contestMemberDTO.setContest(contestDTO.toContestEntity());

        contestMemberService.saveConMember(contestMemberDTO);

        return "redirect:/contest/contestDetail?id=" + id;
    }
    @RequestMapping("/member")//회원 목록
    public String ContestMemberList(@RequestParam("id") Long id, Model model, Authentication authentication) {

        if (authentication==null){
            return "redirect:/member/login";
        }
        ContestDTO contestDTO = contestService.selectContest(id);
        if (!authentication.getName().equals(contestDTO.getMember().getUserId())) {
            return "/accessBlock";
        }
        List<ContestMemberDTO> contestMemberDTOList = contestMemberService.selectContestMember(id);

        model.addAttribute("contestDTO", contestDTO);
        model.addAttribute("List", contestMemberDTOList);

        return "contest/member";
    }
}
