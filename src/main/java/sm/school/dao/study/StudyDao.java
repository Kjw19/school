package sm.school.dao.study;

import sm.school.domain.study.Study;

import java.util.List;
import java.util.Optional;

public interface StudyDao {

    List<Study> findAll();

    Study save(Study study);

    Optional<Study> findById(Long id);

    Study findStudyById(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);

    List<Study> findStudyByMemberUserId(String userId);
}
