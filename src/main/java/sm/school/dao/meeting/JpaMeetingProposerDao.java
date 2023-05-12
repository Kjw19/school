package sm.school.dao.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.MeetingProposerRepository;
import sm.school.domain.meeting.MeetingProposer;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMeetingProposerDao implements MeetingProposerDao{

    private final MeetingProposerRepository meetingProposerRepository;


    @Override
    public List<MeetingProposer> findAll() {
        return meetingProposerRepository.findAll();
    }

    @Override
    public Optional<MeetingProposer> findById(Long id) {
        return meetingProposerRepository.findById(id);
    }

    @Override
    public MeetingProposer save(MeetingProposer meetingProposer) {
        return meetingProposerRepository.save(meetingProposer);
    }

    @Override
    public List<MeetingProposer> findByMeetingsId(Long id) {
        return meetingProposerRepository.findByMeetingsId(id);
    }

    @Override
    public void deleteById(Long id) {
        meetingProposerRepository.deleteById(id);
    }
}
