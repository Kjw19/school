package sm.school.dao.study;

import sm.school.domain.study.StudyMember;

import java.util.List;
import java.util.Optional;

public interface StudyMemberDao {

    List<StudyMember> findAll();

    StudyMember save(StudyMember studyMember);

    Optional<StudyMember> findById(Long id);

    StudyMember findStudyMemberById(Long id);

    List<StudyMember> findByStudyId(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);






}
