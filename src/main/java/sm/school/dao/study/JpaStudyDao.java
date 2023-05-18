package sm.school.dao.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.StudyRepository;
import sm.school.domain.study.Study;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaStudyDao implements StudyDao {

    private final StudyRepository studyRepository;

    @Override
    public List<Study> findAll() {
        return studyRepository.findAll();
    }

    @Override
    public Study save(Study study) {
        return studyRepository.save(study);
    }

    @Override
    public Optional<Study> findById(Long id) {
        return studyRepository.findById(id);
    }

    @Override
    public Study findStudyById(Long id) {
        return studyRepository.findStudyById(id);
    }

    @Override
    public void deleteById(Long id) {
        studyRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return studyRepository.existsById(id);
    }

    @Override
    public List<Study> findStudyByMemberUserId(String userId) {
        return studyRepository.findStudyByMemberUserId(userId);
    }
}
