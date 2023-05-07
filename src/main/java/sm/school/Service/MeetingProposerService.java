package sm.school.Service;

import lombok.RequiredArgsConstructor;
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

    public List<MeetingProposer> findAllMeetingProposer() {
        List<MeetingProposer> meetingProposerList = meetingProposerRepository.findAll();

        return meetingProposerList;
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

    public Boolean deleteProposer(Long id) {
        try {
            meetingProposerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
