package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MeetingService;
import sm.school.dto.meeting.MeetingProposerDTO;
import sm.school.dto.meeting.MeetingProposerDetailDTO;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/meetingPro")
@RequiredArgsConstructor
public class MeetingProposerController {

    private final MeetingService meetingService;


    @GetMapping("/detail")
    public String meetingProposerList(@RequestParam Long id, Model model, Authentication authentication) throws AccessDeniedException {

            MeetingProposerDetailDTO meetingProposerDetail = meetingService.getMeetingProposerDetail(id, authentication.getName());
            model.addAttribute("meeting", meetingProposerDetail.getMeetingDTO());
            model.addAttribute("lists", meetingProposerDetail.getMeetingProposerDTOList());

            return "meeting/proposer";


    }

    @GetMapping("/create")
    public String createMeetingProposerForm(@RequestParam("id") Long id,
                                            @ModelAttribute("meetingProposerDTO") MeetingProposerDTO meetingProposerDTO) {
        return "meeting/proposerCreate";
    }

    @PostMapping("/create")
    public String createMeetingProposer(@RequestParam("id") Long id, @Valid MeetingProposerDTO meetingProposerDTO,
                                        Authentication authentication) {

        meetingService.createMeetingProposer(meetingProposerDTO, authentication, id);

        return "redirect:/meeting/detail?id=" + id;
    }

    @RequestMapping("/select")
    public String selectProposer(@RequestParam Long id, @RequestParam Long meetId, Authentication authentication) throws AccessDeniedException {

        meetingService.approvalProposer(id, meetId, authentication.getName());

        return "redirect:/meetingPro/detail?id=" + meetId;
    }



    @RequestMapping("/delete")
    public String proposerDelete(@RequestParam Long id, @RequestParam Long meetId, Authentication authentication) throws AccessDeniedException {
            meetingService.deleteProposerIfAuthorized(id, meetId, authentication.getName());

            return "redirect:/meetingPro/detail?id=" + meetId;

    }
}
