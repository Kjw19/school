package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MemberService;
import sm.school.dto.MemberDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("memberDTO") MemberDTO memberDTO) {

        return "member/signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDTO, BindingResult bindingResult) {

        memberService.signUp(memberDTO, bindingResult);


        return "redirect:/";
    }

    //ResponseEntity는 HTTP응답을 생성하기 위해 사용되는 클래스이며, 반환할 값과
    //HTTP 응답 상태 코드를 포함할 수 있다.
    //ResponseEntity.ok를 사용하여 HTTP응답 상태 코드를 200 OK로 설정한다.
    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userId));
    }
}
