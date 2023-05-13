package sm.school.dao.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.StudyMemberRepository;
import sm.school.domain.study.StudyMember;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaStudyMemberDao implements StudyMemberDao{

    private final StudyMemberRepository studyMemberRepository;

    @Override
    public List<StudyMember> findAll() {
        return studyMemberRepository.findAll();
    }

    @Override
    public StudyMember save(StudyMember studyMember) {
        return studyMemberRepository.save(studyMember);
    }

    @Override
    public Optional<StudyMember> findById(Long id) {
        return studyMemberRepository.findById(id);
    }

    @Override
    public StudyMember findStudyMemberById(Long id) {
        return studyMemberRepository.findStudyMemberById(id);
    }

    @Override
    public List<StudyMember> findByStudyId(Long id) {
        return studyMemberRepository.findByStudyId(id);
    }

    @Override
    public void deleteById(Long id) {
        studyMemberRepository.deleteById(id);

    }

    @Override
    public Boolean existsById(Long id) {
        return studyMemberRepository.existsById(id);
    }
}
