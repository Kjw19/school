package sm.school.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MemberDetailsService;
import sm.school.Service.StudyMemberService;
import sm.school.Service.StudyService;
import sm.school.domain.member.Member;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/studyMember")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    private final StudyService studyService;


    @GetMapping("/create")
    public String createStudyMemberForm(@RequestParam("id") Long id,
                                        @ModelAttribute("studyMemberDTO") StudyMemberDTO studyMemberDTO,
                                        Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }
        return "study/register";
    }

    @PostMapping("/create")
    public String createStudyMember(@RequestParam("id") Long id,
                                    @Valid StudyMemberDTO studyMemberDTO,
                                    Authentication authentication) {
        if (authentication == null) {
            return "/member/login";
        }
        Member member = ((MemberDetailsService) authentication.getPrincipal()).getMember();
        StudyDTO studyDTO = studyService.selectStudy(id);


        studyMemberDTO.setMember(member);
        studyMemberDTO.setStudy(studyDTO.toStudyEntity());
        studyMemberService.createStudyMember(studyMemberDTO);

        return "redirect:/study/detail?id=" + id;
    }

    @RequestMapping("/member")//회원 목록
    public String StudyMemberList(@RequestParam("id") Long id, Model model, Authentication authentication) {

        if (authentication == null) {
            return "redirect:/member/login";
        }
        StudyDTO studyDTO = studyService.selectStudy(id);
        if (!authentication.getName().equals(studyDTO.getMember().getUserId())) {
            return "/accessBlock";
        }

        List<StudyMemberDTO> studyMemberDTOList = studyMemberService.selectStudyMember(id);

        model.addAttribute("studyDTO", studyDTO);
        model.addAttribute("List", studyMemberDTOList);

        return "study/member";
    }

    @RequestMapping("/access")
    public String accessMember(@RequestParam Long id, @RequestParam Long studyId, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }

        StudyDTO studyDTO = studyService.selectStudy(studyId);
        if (!authentication.getName().equals(studyDTO.getMember().getUserId())) {
            return "redirect:/accessBlock";
        }

        studyMemberService.accessMember(id);

        return "redirect:/studyMember/member?id=" + studyId;
    }

    @RequestMapping("/delete")
    public String deleteMember(@RequestParam Long id, @RequestParam Long studyId, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }

        StudyDTO studyDTO = studyService.selectStudy(studyId);
        if (!authentication.getName().equals(studyDTO.getMember().getUserId())) {
            return "redirect:/accessBlock";
        }

        Boolean deleteMember = studyMemberService.deleteMember(id);
        if (deleteMember) {
            return "redirect:/studyMember/member?id=" + studyId;
        } else {
            return "redirect:/errorPage";
        }
    }
}
