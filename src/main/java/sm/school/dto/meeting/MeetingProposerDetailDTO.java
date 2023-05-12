package sm.school.dto.meeting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MeetingProposerDetailDTO {

    private MeetingDTO meetingDTO;
    private List<MeetingProposerDTO> meetingProposerDTOList;
}
