package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sm.school.Service.MemberDetailsService;
import sm.school.Service.StudyService;
import sm.school.dto.StudyDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/list")
    public String StudyList(Model model) {
        List<StudyDTO> allStudy = studyService.findAllStudy();

        model.addAttribute("Lists", allStudy);

        return "study/studyList";
    }

    @GetMapping("/create")
    public String CreateStudyForm(@ModelAttribute("studyDTO") StudyDTO studyDTO, Authentication authentication) {
        if (authentication == null) {
            return "member/login";
        }
        return "study/studyCreate";
    }

    @PostMapping("/create")
    public String CreateStudy(@Valid StudyDTO studyDTO, Authentication authentication) {
        if (authentication == null) {
            return "member/login";
        }

        studyDTO.setMember(((MemberDetailsService) authentication.getPrincipal()).getMember());

        studyService.createStudy(studyDTO);

        return "redirect:/study/list";
    }

}
