package sm.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.school.domain.study.Study;
import sm.school.dto.StudyDTO;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Study findStudyById(Long id);

    Boolean deleteStudiesById(Long id);

    List<Study> findStudyByMemberUserId(String userId);

    Boolean existsByName(String name);
}
