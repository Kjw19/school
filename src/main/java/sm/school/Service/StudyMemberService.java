package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.StudyMemberRepository;
import sm.school.Repository.StudyRepository;
import sm.school.domain.study.StudyMember;
import sm.school.dto.StudyMemberDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    private final StudyRepository studyRepository;

    public StudyMember createStudyMember(StudyMemberDTO studyMemberDTO) {

        StudyMember studyMember = studyMemberDTO.toStudyMember();

        return studyMemberRepository.save(studyMember);
    }

    public List<StudyMemberDTO> selectStudyMember(Long id) {//스터디 멤버 불러오기

        List<StudyMember> studyDTOS = studyMemberRepository.findByStudyId(id);
        List<StudyMemberDTO> studyMemberDTOList = new ArrayList<>();

        for (StudyMember studyMember: studyDTOS) {
            studyMemberDTOList.add(studyMember.toStudyMemberDTO());
        }

        return studyMemberDTOList;
    }

    public void accessMember(Long id) {
        StudyMember studyMemberById = studyMemberRepository.findStudyMemberById(id);
        studyMemberById.ModifyStudyRole(1);
    }

    public Boolean deleteMember(Long id) {
        try {
            studyMemberRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
