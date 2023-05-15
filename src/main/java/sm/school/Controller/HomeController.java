package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sm.school.Service.MemberService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Authentication authentication){
        return "index";
    }
    @GetMapping("/accessBlock")
    public String adminBlock() {

        return "accessBlock";
    }
    @GetMapping("/errorPage")
    public String errorPage() {

        return "errorPage";
    }
}
