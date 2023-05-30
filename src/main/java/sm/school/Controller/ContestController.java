package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.Contest.ContestMemberService;
import sm.school.Service.Contest.ContestService;
import sm.school.Service.MemberDetailsService;
import sm.school.domain.contest.ContestDTO;
import sm.school.domain.member.Member;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contest")
@RequiredArgsConstructor
@Slf4j
public class ContestController {
    private final ContestService contestService;




    @GetMapping("/")
    public String home(Model model){
        List<ContestDTO> allContest = contestService.ListContest();

        model.addAttribute("list", allContest);

        return "contest/list";
    }
    @GetMapping("/create")
    public String createContestForm(@ModelAttribute("contestDTO")ContestDTO contestDTO){
        return "contest/create";
    }


    @PostMapping("/create")
    public String createContest(@Valid ContestDTO contestDTO, @RequestParam("profileImg") MultipartFile multipartFile, Authentication authentication){

        contestService.creatContest(contestDTO,multipartFile,authentication);


        return "redirect:/";
    }
    @GetMapping("/{name}/exists")
    public ResponseEntity<Boolean> checkStudyNameDuplicate(@PathVariable String name) {
        return ResponseEntity.ok(contestService.checkStudyNameDuplicate(name));
    }

    @GetMapping("/detail")
    public String contestDetail(@RequestParam("id") long id, Model model) {


        model.addAttribute("contestDTO", contestService.selectContest(id));


        return "contest/contestDetail";
    }
    @GetMapping("/update")
    public String contestUpdateForm(@RequestParam Long id,Model model){

        ContestDTO contestDTO = contestService.selectContest(id);
        model.addAttribute("contestDTO", contestDTO);
        return "contest/update";
    }
    @PostMapping("/update")
    public String contestUpdate(@ModelAttribute("contestDTO") ContestDTO contestDTO,Authentication authentication,
                                @RequestParam("profileImg") MultipartFile multipartFile)throws AccessDeniedException {

        contestService.updateContest(contestDTO,authentication.getName(), multipartFile);

        return "redirect:/contest/detail?id=" + contestDTO.getId();
    }
    @RequestMapping("/delete")
    public String contestDelete(@RequestParam("id") Long id, Authentication authentication) throws AccessDeniedException {
        contestService.deleteContest(id, authentication.getName());
        return "redirect:/";
    }

    @GetMapping("currentImage")
    public ResponseEntity<Map<String, String>> currentProfile(@RequestParam("id") Long id, Authentication authentication) {
        String currentImage = contestService.currentImage(id);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", currentImage);

        return ResponseEntity.ok(response);
    }


}
