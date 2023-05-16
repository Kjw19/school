package sm.school.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.MeetingService;
import sm.school.dto.meeting.MeetingDTO;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String CreateMeetForm(@ModelAttribute("meetingDTO") MeetingDTO meetingDTO) {

        return "meeting/createMeeting";
    }

    @PostMapping("/create") //authentication으로 정보를 받아옴
    public String CreateMeet(@Valid MeetingDTO meetingDTO, @RequestParam("profileImg") MultipartFile multipartFile,
                             Authentication authentication) {

        meetingService.createMeeting(meetingDTO, multipartFile,authentication);

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
    public String updateMeeting(@ModelAttribute("meetingDTO") MeetingDTO meetingDTO,
                                @RequestParam("profileImg") MultipartFile multipartFile) {

        meetingService.updateMeeting(meetingDTO, multipartFile);

        return "redirect:/meeting/detail?id=" + meetingDTO.getId();
    }

    @RequestMapping("/delete")
    public String deleteMeeting(@RequestParam("id") Long id, Authentication authentication) throws AccessDeniedException {
        meetingService.deleteMeeting(id, authentication.getName());

        return "redirect:/meeting/list";
    }

    @GetMapping("currentImage")
    public ResponseEntity<Map<String, String>> currentProfile(@RequestParam("id") Long id, Authentication authentication) {
        String currentImage = meetingService.currentImage(id);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", currentImage);

        return ResponseEntity.ok(response);
    }
}
