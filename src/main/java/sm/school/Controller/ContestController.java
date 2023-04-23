package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sm.school.Service.Contest.ContestService;
import sm.school.domain.contest.ContestDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/contest")
@RequiredArgsConstructor
@Slf4j
public class ContestController {
    private final ContestService contestService;

    @GetMapping("/home")
    public String home(){
        return "contest/contest";
    }
    @GetMapping("/create")
    public String create(@ModelAttribute("contestDTO")ContestDTO contestDTO){
        return "contest/create";
    }
    @PostMapping("/create")
    public String create(@Valid ContestDTO contestDTO, BindingResult result){

        if(result.hasErrors()){
            return "contest/create";
        }

        contestService.creatContest(contestDTO);

        return "redirect:/";
    }


}
