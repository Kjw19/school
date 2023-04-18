package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MemberService;
import sm.school.domain.member.Member;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(@ModelAttribute("member") Member member) {

        return "member/signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        memberService.signUp(member);


        return "redirect:/";
    }

    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userId));
    }
}
