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
import sm.school.domain.member.Member;

import javax.validation.Valid;

@Controller
@RequestMapping("/contest")
@RequiredArgsConstructor
@Slf4j
public class ContestController {
    private final ContestService contestService;

    private final ContestMemberService contestMemberService;


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("contests", contestService.ListContest());
        return "contest/list";
    }
    @GetMapping("/create")
    public String create(@ModelAttribute("contestDTO")ContestDTO contestDTO){
        return "contest/create";
    }


    @PostMapping("/create")
    public String create(@Valid ContestDTO contestDTO, Authentication authentication){

        if (authentication == null) {
            return "redirect:/member/login"; //로그인 검증
        }

        //사용자 정보를 받아 memberDetails로 저장
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        Member member = memberDetails.getMember();
        contestDTO.setMember(member);

        contestService.creatContest(contestDTO);


        return "redirect:/";
    }

    @GetMapping("/detail")
    public String contestDetail(@RequestParam("id") Long id, Model model) {


        model.addAttribute("contest", contestService.selectContest(id));
        model.addAttribute("contestPro", contestMemberService.findContestMemberByConId(id));

        return "contest/contestDetail";
    }
    @GetMapping("/update")
    public String contestUpdateForm(@RequestParam("id") Long id,Model model,Authentication authentication){
        if (authentication==null){
            return "redirect:/";
        }
        ContestDTO contestDTO = contestService.selectContest(id);
        model.addAttribute("contestDTO", contestDTO);
        return "contest/update";
    }
    @PostMapping("/update")
    public String contestUpdate(@ModelAttribute("contestDTO") ContestDTO contestDTO,Authentication authentication){
        if (authentication == null){
            return "redirect:/member/login";
        }
        contestService.updateContest(contestDTO);

        return "redirect:/contest/";
    }
    @RequestMapping("/delete")
    public String contestDelete(@RequestParam("id") Long id) {

        Boolean deleteBoard = contestService.deleteContest(id);

        if (deleteBoard){
            return "redirect:/contest/";
        }else {
            return "redirect:/errorPage";
        }
    }


}
