package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MemberDetailsService;
import sm.school.Service.StudyService;
import sm.school.dto.StudyDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/detail")
    public String studyDetail(@RequestParam("id") long id, Model model) {
        model.addAttribute("studyDTO", studyService.selectStudy(id));

        return "study/studyDetail";
    }


    @GetMapping("/update")
    public String studyUpdateForm(@RequestParam Long id, Model model, Authentication authentication) {

        if (authentication == null) {
            return "redirect:/member/login";
        }

        StudyDTO studyDTO = studyService.selectStudy(id);
        model.addAttribute("studyDTO", studyDTO);

        return "study/studyUpdate";
    }

    @PostMapping("/update")
    public String studyUpdate(@ModelAttribute("studyDTO") StudyDTO studyDTO, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }
        studyService.updateStudy(studyDTO);

        return "redirect:/study/detail?id=" + studyDTO.getId();
    }

    @RequestMapping("/delete")
    public String studyDelete(@RequestParam("id") Long id) {
        Boolean deleteStudy = studyService.deleteStudy(id);

        if (deleteStudy) {
            return "redirect:/study/list";
        } else {
            return "redirect:/errorPage";
        }
    }
}