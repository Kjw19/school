package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.Contest.ContestMemberService;
import sm.school.Service.Contest.ContestService;
import sm.school.Service.MemberDetailsService;
import sm.school.domain.contest.ContestDTO;
import sm.school.domain.contest.ContestMemberDTO;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequestMapping("/contestMember")
@RequiredArgsConstructor
@Slf4j
public class ContestMemberController {

    private final ContestService contestService;

    @GetMapping("/create")

    public String createConMemFrom(@RequestParam("id") Long id,
                                   @ModelAttribute("contestMemberDTO")ContestMemberDTO contestMemberDTO){
        log.info("회원생성폼 시작 {}", contestMemberDTO.getRole());

        return "contest/memberCreate";
    }
    @PostMapping("/create")
    public String createConMem(@RequestParam("id") Long id,
                               @Valid ContestMemberDTO contestMemberDTO,
                                        Authentication authentication) {
        contestService.createContestMember(id, contestMemberDTO, authentication);

        return "redirect:/contest/detail?id=" + id;
    }
    @RequestMapping("/member")//회원 목록
    public String ContestMemberList(@RequestParam("id") Long id, Model model, Authentication authentication)throws AccessDeniedException {

        ContestDTO contestDTO = contestService.selectContest(id);
        List<ContestMemberDTO> contestMemberDTOList = contestService.selectContestMemberList(id, authentication.getName());

        model.addAttribute("contestDTO", contestDTO);
        model.addAttribute("List", contestMemberDTOList);

        return "contest/member";
    }
    @RequestMapping("/access")
    public String accessMember(@RequestParam Long id, @RequestParam Long contestId, Authentication authentication) {
        contestService.accessMember(id, authentication.getName());

        return "redirect:/contestMember/member?id=" + contestId;
    }

    @RequestMapping("/delete")
    public String deleteMember(@RequestParam Long id, @RequestParam Long contestId, Authentication authentication) throws AccessDeniedException {

        contestService.deleteMember(id,contestId, authentication.getName());
        return "redirect:/contestMember/member?id=" + contestId;
    }
}
