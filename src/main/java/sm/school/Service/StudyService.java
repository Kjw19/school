package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.StudyRepository;
import sm.school.domain.study.Study;
import sm.school.dto.StudyDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService{

    private final StudyRepository studyRepository;

    public Study createStudy(StudyDTO studyDTO) {

        Study study = studyDTO.toStudyEntity();

        return studyRepository.save(study);
    }

    public List<StudyDTO> findAllStudy() {
        List<Study> studyList = studyRepository.findAll();
        List<StudyDTO> studyDTOList = new ArrayList<>();

        for (Study study : studyList) {
            studyDTOList.add(study.toStudyDTO());
        }
        return studyDTOList;
    }

    public StudyDTO selectStudy(Long id) {
        Study selectedStudy = studyRepository.findStudyById(id);
        StudyDTO selectedStudyDTO = selectedStudy.toStudyDTO();

        return selectedStudyDTO;
    }

    public void updateStudy(StudyDTO studyDTO) {
        Study study = studyRepository.findStudyById(studyDTO.getId());

        study.UpdateStudy(studyDTO.getName(), studyDTO.getContent(),studyDTO.getRegion(), studyDTO.getRegType());
    }

    public Boolean deleteStudy(Long id) {
        try {
            studyRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }


}
