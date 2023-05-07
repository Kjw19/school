package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MeetingProposerService;
import sm.school.Service.MeetingService;
import sm.school.Service.MemberDetailsService;
import sm.school.dto.MeetingDTO;
import sm.school.dto.MeetingProposerDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/meetingPro")
@RequiredArgsConstructor
public class MeetingProposerController {

    private final MeetingProposerService meetingProposerService;

    private final MeetingService meetingService;


    @GetMapping("/detail")
    public String MeetingProposerList(@RequestParam Long id, Model model, Authentication authentication) {

        model.addAttribute("lists",  meetingProposerService.selectMeetingProposer(id));
        model.addAttribute("meeting",  meetingService.selectMeeting(id));

        return "meeting/proposer";
    }

    @GetMapping("/create")
    public String createMeetingProposerForm(@RequestParam("id") Long id, @ModelAttribute("meetingProposerDTO") MeetingProposerDTO meetingProposerDTO,
                                            Authentication authentication) {

        if (authentication == null) {
            return "redirect:/member/login";
        }
        return "meeting/proposerCreate";
    }

    @PostMapping("/create")
    public String createMeetingProposer(@RequestParam("id") Long id, @Valid MeetingProposerDTO meetingProposerDTO,
                                        Authentication authentication) {
        if (authentication == null) {


            return "redirect:/member/login";
        }

        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        meetingProposerDTO.setMember(memberDetails.getMember());

        MeetingDTO meetingDTO = meetingService.selectMeeting(id);

        meetingProposerDTO.setMeetings(meetingDTO.toMeetingEntity());

        meetingProposerService.createMeetingProposer(meetingProposerDTO);

        return "redirect:/meeting/detail?id=" + id;
    }

    @RequestMapping("/select")
    public String proposerSelect(@RequestParam Long id, @RequestParam Long meetId) {

        meetingProposerService.selectProposer(id);
        meetingProposerService.afterDeleteToSelect(meetId);

        return "redirect:/meetingPro/detail?id=" + meetId;
    }
    @RequestMapping("/delete")
    public String proposerDelete(@RequestParam Long id) {

        Boolean deleteProposer = meetingProposerService.deleteProposer(id);

        if (deleteProposer) {
            return "redirect:/meeting/";
        } else {
            return "redirect:/accessBlock";
        }
    }
}
