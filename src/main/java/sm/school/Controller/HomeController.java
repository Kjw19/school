package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sm.school.Service.MemberService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {

        memberService.findMember(authentication.getName());

        model.addAttribute("member", memberService.findMember(authentication.getName()));
        return "index";
    }
    @GetMapping("/accessBlock")
    public String adminBlock() {

        return "accessBlock";
    }
}
