package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sm.school.Service.MeetingService;
import sm.school.Service.MemberService;
import sm.school.Service.StudyService;
import sm.school.domain.enumType.MemRole;
import sm.school.dto.MemberDTO;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final MeetingService meetingService;
    private final StudyService studyService;

    @GetMapping

    public String admin() {

        return "admin/adminPage";
    }

    @GetMapping("/member")
    public String manageMember(Model model) {

        List<MemberDTO> memberDTOList = memberService.memberList();

        model.addAttribute("List", memberDTOList);

        return "admin/manageMember";
    }

    @RequestMapping("changeRole")
    public String changeRole(@RequestParam("id") Long id) {

        memberService.changeRole(id);

        return "redirect:/admin/member";
    }

    @GetMapping("/meeting")
    public String manageMeeting(Model model) {
        model.addAttribute("List", meetingService.findMeeting());

        return "/admin/manageMeeting";
    }

    @RequestMapping("deleteMeeting")
    public String deleteMeeting(@RequestParam("id") Long id) {


        Boolean deleteMeeting = meetingService.DeleteMeeting(id);
        if (deleteMeeting) {
            return "redirect:/admin/meeting";
        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/study")
    public String manageStudy(Model model) {

        model.addAttribute("List", studyService.findAllStudy());

        return "admin/manageStudy";
    }

    @RequestMapping("deleteStudy")
    public String deleteStudy(@RequestParam("id") Long id) {

        Boolean deleteStudy = studyService.deleteStudy(id);
        if (deleteStudy) {
            return "redirect:/admin/study";
        } else {
            return "redirect:/errorPage";
        }
    }

}
