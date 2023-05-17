package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.BoardService;
import sm.school.Service.MemberService;
import sm.school.dto.BoardDTO;
import sm.school.dto.member.MemberDTO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;


    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("memberDTO") MemberDTO memberDTO) {

        return "member/signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDTO, @RequestParam("profileImg") MultipartFile multipartFile,
                         BindingResult bindingResult) {
        memberService.signUp(memberDTO, multipartFile, bindingResult);


        return "redirect:/";
    }

    //ResponseEntity는 HTTP응답을 생성하기 위해 사용되는 클래스이며, 반환할 값과
    //HTTP 응답 상태 코드를 포함할 수 있다.
    //ResponseEntity.ok를 사용하여 HTTP응답 상태 코드를 200 OK로 설정한다.
    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userId));
    }

    @GetMapping("/myPage")
    public String myPageForm(Model model, Authentication authentication) {

        model.addAttribute("user", memberService.findMember(authentication.getName()));

        return "/member/myPage";
    }

    @GetMapping("/myPage/modify")
    public String modifyMemberForm(Model model, Authentication authentication) {

        model.addAttribute("memberDTO", memberService.findMember(authentication.getName()));

        return "member/modifyMember";
    }

    @PostMapping("/myPage/modify")
    public String modifyMember(@Valid MemberDTO memberDTO, @RequestParam("profileImg") MultipartFile multipartFile,
                               BindingResult bindingResult, Authentication authentication) {

        memberService.modifyMember(memberDTO, multipartFile, bindingResult, authentication.getName());

        return "redirect:/member/myPage";
    }

    @GetMapping("/currentProfile")
    public ResponseEntity<Map<String, String>> currentProfile(Authentication authentication) {
        String currentProfile = memberService.getCurrentProfile(authentication.getName());

        Map<String, String> response = new HashMap<>();
        response.put("profileImageUrl", currentProfile);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/myPage/board")
    public String findBoardFromUser(Model model, Authentication authentication) {
        List<BoardDTO> boardFromUser = boardService.findBoardFromUser(authentication.getName());
        model.addAttribute("boards", boardFromUser);

        return "/member/boardFromUser";
    }
}
