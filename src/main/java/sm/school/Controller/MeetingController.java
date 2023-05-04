package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MeetingService;
import sm.school.Service.MemberDetailsService;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.member.Member;
import sm.school.dto.MeetingDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/meeting")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/")
    public String MeetingList(Model model) {

        List<Meeting> meeting = meetingService.findMeeting();

        model.addAttribute("meetings", meeting);

        return "meeting/list";
    }

    @GetMapping("/create")
    public String CreateMeetForm(@ModelAttribute("meetingDTO") MeetingDTO meetingDTO, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }

        return "meeting/createMeeting";
    }

    @PostMapping("/create") //authentication으로 정보를 받아옴
    public String CreateMeet(@Valid MeetingDTO meetingDTO, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login"; //로그인 검증
        }

        //사용자 정보를 받아 memberDetails로 저장
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        Member member = memberDetails.getMember();
        meetingDTO.setMember(member);

        meetingService.createMeeting(meetingDTO);

        return "redirect:/";
    }

    @GetMapping("/detail")
    public String meetingDetail(@RequestParam("id") Long id, Model model) {

        model.addAttribute("meeting", meetingService.selectMeeting(id));

        return "meeting/detail";
    }
}
