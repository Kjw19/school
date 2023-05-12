package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.MeetingService;
import sm.school.dto.meeting.MeetingDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/meeting")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {

    private final MeetingService meetingService;



    @GetMapping("/list")
    public String MeetingList(Model model) {

        List<MeetingDTO> meetingDTOList = meetingService.findMeeting();

        model.addAttribute("meetings", meetingDTOList);

        return "meeting/list";
    }

    @GetMapping("/completeList")
    public String MeetingCompleteList(Model model) {

        List<MeetingDTO> meetingDTOList = meetingService.findMeeting();

        model.addAttribute("meetings", meetingDTOList);

        return "meeting/completeList";
    }

    @GetMapping("/create")
    public String CreateMeetForm(@ModelAttribute("meetingDTO") MeetingDTO meetingDTO, Authentication authentication) {

        return "meeting/createMeeting";
    }

    @PostMapping("/create") //authentication으로 정보를 받아옴
    public String CreateMeet(@Valid MeetingDTO meetingDTO, Authentication authentication) {

        meetingService.createMeeting(meetingDTO, authentication);

        return "redirect:/meeting/list";
    }

    @GetMapping("/detail")
    public String meetingDetail(@RequestParam("id") Long id, Model model) {

        model.addAttribute("meeting", meetingService.detailMeeting(id));
        model.addAttribute("meetingPro", meetingService.selectMeetingProposer(id));

        return "meeting/detail";
    }

    @GetMapping("/update")
    public String updateMeetingFrom(@RequestParam("id") Long id, Model model) {


        MeetingDTO meetingDTO = meetingService.detailMeeting(id);
        model.addAttribute("meetingDTO", meetingDTO);
        return "meeting/updateMeeting";
    }

    @PostMapping("/update")
    public String updateMeeting(@ModelAttribute("meetingDTO") MeetingDTO meetingDTO) {

        meetingService.updateMeeting(meetingDTO);

        return "redirect:/meeting/list";
    }

    @DeleteMapping("/delete")
    public String deleteMeeting(@RequestParam("id") Long id) {
        Boolean deleteMeeting = meetingService.DeleteMeeting(id);
        if (deleteMeeting) {
            return "redirect:/meeting/list";
        } else {
            return "redirect:/main";
        }
    }
}
