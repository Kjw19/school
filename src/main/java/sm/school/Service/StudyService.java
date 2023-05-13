package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.dao.study.JpaStudyDao;
import sm.school.dao.study.JpaStudyMemberDao;
import sm.school.domain.study.Study;
import sm.school.domain.study.StudyMember;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;

import java.util.ArrayList;
import java.util.List;
import static sm.school.Service.commonConst.Status.SELECTED;


@Service
@Transactional
@RequiredArgsConstructor
public class StudyService{

    private final JpaStudyDao jpaStudyDao;
    private final JpaStudyMemberDao jpaStudyMemberDao;

    private final CommonService commonService;

    public Study createStudy(StudyDTO studyDTO, Authentication authentication) {

        studyDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Study study = studyDTO.toStudyEntity();

        return jpaStudyDao.save(study);
    }

    public List<StudyDTO> findAllStudy() {
        List<Study> studyList = jpaStudyDao.findAll();
        List<StudyDTO> studyDTOList = new ArrayList<>();

        for (Study study : studyList) {
            studyDTOList.add(study.toStudyDTO());
        }
        return studyDTOList;
    }

    public StudyDTO detailStudy(Long id) {
        Study selectedStudy = jpaStudyDao.findStudyById(id);
        StudyDTO selectedStudyDTO = selectedStudy.toStudyDTO();

        return selectedStudyDTO;
    }

    public void updateStudy(StudyDTO studyDTO) {
        Study study = jpaStudyDao.findStudyById(studyDTO.getId());

        study.UpdateStudy(studyDTO.getName(), studyDTO.getContent(),studyDTO.getRegion(), studyDTO.getRegType());
    }

    public void deleteStudy(Long id) {

        if (!jpaStudyDao.existsById(id)) {
            throw new DataNotFoundException();
        }
        List<StudyMember> studyMemberList = jpaStudyMemberDao.findByStudyId(id);

        for (StudyMember studyMember: studyMemberList) {
            jpaStudyMemberDao.deleteById(studyMember.getId());
        }
        jpaStudyDao.deleteById(id);
    }

    //스터디 회원

    public StudyMember createStudyMember(Long id,
                                         StudyMemberDTO studyMemberDTO,
                                         Authentication authentication) {
        StudyDTO studyDTO = detailStudy(id);

        studyMemberDTO.setStudy(studyDTO.toStudyEntity());
        studyMemberDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        StudyMember studyMember = studyMemberDTO.toStudyMember();

        return jpaStudyMemberDao.save(studyMember);
    }

    public List<StudyMemberDTO> selectStudyMember(Long id) {//스터디 멤버 선택

        List<StudyMember> studyDTOS = jpaStudyMemberDao.findByStudyId(id);
        List<StudyMemberDTO> studyMemberDTOList = new ArrayList<>();

        for (StudyMember studyMember: studyDTOS) {
            studyMemberDTOList.add(studyMember.toStudyMemberDTO());
        }

        return studyMemberDTOList;
    }

    public void accessMember(Long id) {
        StudyMember studyMemberById = jpaStudyMemberDao.findStudyMemberById(id);
        studyMemberById.ModifyStudyRole(SELECTED);
    }


    public void deleteMember(Long id) {

        if (!jpaStudyMemberDao.existsById(id)) {
            throw new MemberNotExistException();
        }
        jpaStudyMemberDao.deleteById(id);
    }


}
