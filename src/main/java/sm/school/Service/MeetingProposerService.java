package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.MeetingProposerRepository;
import sm.school.domain.meeting.MeetingProposer;
import sm.school.dto.MeetingProposerDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingProposerService {

    private final MeetingProposerRepository meetingProposerRepository;

    public MeetingProposer createMeetingProposer(MeetingProposerDTO meetingProposerDTO) {

        MeetingProposer meetingProposer = meetingProposerDTO.toMeetingProposer();

        return meetingProposerRepository.save(meetingProposer);
    }

    //meetings의 id값을 통해 MeetingProposer값을 찾는다.
    public List<MeetingProposer> getMeetingProposersByMeetingId(Long id) {
        return meetingProposerRepository.findByMeetingsId(id);
    }

    public List<MeetingProposerDTO> selectMeetingProposer(Long id) {

        List<MeetingProposer> meetingProposers = meetingProposerRepository.findByMeetingsId(id);
        List<MeetingProposerDTO> meetingProposerDTOList = new ArrayList<>();

        for (MeetingProposer meetingProposer: meetingProposers) {
            meetingProposerDTOList.add(meetingProposer.toMeetingProposerDTO());
        }
        return meetingProposerDTOList;
    }

    public void selectProposer(Long id) {

        MeetingProposer meetingProposer = meetingProposerRepository.findMeetingProposerById(id);
        meetingProposer.changeStatus(1);
    }

    public void afterDeleteToSelect(Long meetId) {
        List<MeetingProposer> byMeetingsIds = meetingProposerRepository.findByMeetingsId(meetId);
        for (MeetingProposer byMeetingsId: byMeetingsIds) {
            if (byMeetingsId.getStatus() == 0) {
                meetingProposerRepository.deleteById(byMeetingsId.getId());
            }
        }
    }

    public Boolean deleteProposer(Long id) {
        try {
            meetingProposerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
