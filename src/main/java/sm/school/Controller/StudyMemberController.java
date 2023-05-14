package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.StudyService;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequestMapping("/studyMember")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyService studyService;


    @GetMapping("/create")
    public String createStudyMemberForm(@RequestParam("id") Long id,
                                        @ModelAttribute("studyMemberDTO") StudyMemberDTO studyMemberDTO) {
        return "study/register";
    }

    @PostMapping("/create")
    public String createStudyMember(@RequestParam("id") Long id,
                                    @Valid StudyMemberDTO studyMemberDTO,
                                    Authentication authentication) {

        studyService.createStudyMember(id, studyMemberDTO, authentication);

        return "redirect:/study/detail?id=" + id;
    }

    @RequestMapping("/member")//회원 목록
    public String StudyMemberList(@RequestParam("id") Long id, Model model, Authentication authentication) throws AccessDeniedException {

        StudyDTO studyDTO = studyService.detailStudy(id);
        List<StudyMemberDTO> studyMemberDTOList = studyService.selectStudyMemberList(id, authentication.getName());

        model.addAttribute("studyDTO", studyDTO);
        model.addAttribute("List", studyMemberDTOList);

        return "study/member";
    }

    @RequestMapping("/access")
    public String accessMember(@RequestParam Long id, @RequestParam Long studyId, Authentication authentication) {
        studyService.accessMember(id, authentication.getName());

        return "redirect:/studyMember/member?id=" + studyId;
    }

    @RequestMapping("/delete")
    public String deleteMember(@RequestParam Long id, @RequestParam Long studyId, Authentication authentication) throws AccessDeniedException {

        studyService.deleteMember(id,studyId, authentication.getName());
        return "redirect:/study/studyDetail/id=" + studyId;
    }
}
