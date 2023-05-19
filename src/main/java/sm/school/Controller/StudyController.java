package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.MemberDetailsService;
import sm.school.Service.StudyService;
import sm.school.dto.StudyDTO;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String CreateStudyForm(@ModelAttribute("studyDTO") StudyDTO studyDTO) {
        return "study/studyCreate";
    }

    @PostMapping("/create")
    public String CreateStudy(@Valid StudyDTO studyDTO, @RequestParam("profileImg") MultipartFile multipartFile, Authentication authentication) {
        studyService.createStudy(studyDTO,multipartFile,authentication);
        return "redirect:/study/list";
    }

    @GetMapping("/{name}/exists")
    public ResponseEntity<Boolean> checkStudyNameDuplicate(@PathVariable String name) {
        return ResponseEntity.ok(studyService.checkStudyNameDuplicate(name));
    }


    @GetMapping("/detail")
    public String studyDetail(@RequestParam("id") long id, Model model) {

        model.addAttribute("studyDTO", studyService.detailStudy(id));

        return "study/studyDetail";
    }


    @GetMapping("/update")
    public String studyUpdateForm(@RequestParam Long id, Model model) {

        model.addAttribute("studyDTO", studyService.detailStudy(id));

        return "study/studyUpdate";
    }

    @PostMapping("/update")
    public String studyUpdate(@ModelAttribute("studyDTO") StudyDTO studyDTO,
                              Authentication authentication,
                              @RequestParam("profileImg") MultipartFile multipartFile) throws AccessDeniedException {
        studyService.updateStudy(studyDTO, authentication.getName(), multipartFile);

        return "redirect:/study/detail?id=" + studyDTO.getId();
    }

    @RequestMapping("/delete")
    public String studyDelete(@RequestParam("id") Long id, Authentication authentication) throws AccessDeniedException {
        studyService.deleteStudy(id, authentication.getName());
        return "redirect:/study/list";
    }

    @GetMapping("currentImage")
    public ResponseEntity<Map<String, String>> currentProfile(@RequestParam("id") Long id, Authentication authentication) {
        String currentImage = studyService.currentImage(id);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", currentImage);

        return ResponseEntity.ok(response);
    }
}
