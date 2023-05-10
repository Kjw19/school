package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.study.StudyMember;

import java.util.List;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findByStudyId(Long id);

    StudyMember findStudyMemberById(Long id);
}
