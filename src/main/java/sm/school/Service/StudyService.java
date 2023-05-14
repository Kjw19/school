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

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;


@Service
@Transactional
@RequiredArgsConstructor
public class StudyService{

    private final JpaStudyDao jpaStudyDao;
    private final JpaStudyMemberDao jpaStudyMemberDao;

    private final CommonService commonService;


    @Transactional(readOnly = true)
    public void validateUserAccess(Long studyId, String userId) throws AccessDeniedException {
        StudyDTO studyDTO = detailStudy(studyId);
        if (!userId.equals(studyDTO.getMember().getUserId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }else if (!userId.equals("admin")) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    public Study createStudy(StudyDTO studyDTO, Authentication authentication) {

        studyDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Study study = studyDTO.toStudyEntity();

        return jpaStudyDao.save(study);
    }

    public List<StudyDTO> findAllStudy() {
        return jpaStudyDao.findAll().stream()
                .map(Study :: toStudyDTO)
                .collect(Collectors.toList());
    }

    public StudyDTO detailStudy(Long studyId) {
        Study selectedStudy = jpaStudyDao.findStudyById(studyId);
        StudyDTO selectedStudyDTO = selectedStudy.toStudyDTO();

        return selectedStudyDTO;
    }

    public void updateStudy(StudyDTO studyDTO, String userId) throws AccessDeniedException {
        validateUserAccess(studyDTO.getId(), userId);
        Study study = jpaStudyDao.findStudyById(studyDTO.getId());

        study.UpdateStudy(studyDTO.getName(), studyDTO.getContent(),studyDTO.getRegion(), studyDTO.getRegType());
    }

    public void deleteStudy(Long studyId, String userId) throws AccessDeniedException {

        validateUserAccess(studyId, userId);

        if (!jpaStudyDao.existsById(studyId)) {
            throw new DataNotFoundException();
        }
        List<StudyMember> studyMemberList = jpaStudyMemberDao.findByStudyId(studyId);

        for (StudyMember studyMember: studyMemberList) {
            jpaStudyMemberDao.deleteById(studyMember.getId());
        }
        jpaStudyDao.deleteById(studyId);
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

    public List<StudyMemberDTO> selectStudyMemberList(Long studyId, String userId) throws AccessDeniedException {//스터디 멤버 선택

        validateUserAccess(studyId, userId);

        return jpaStudyMemberDao.findByStudyId(studyId).stream()
                .map(StudyMember :: toStudyMemberDTO)
                .collect(Collectors.toList());
    }

    public void accessMember(Long studyMemberId, String userId) {
        StudyMember studyMemberById = jpaStudyMemberDao.findStudyMemberById(studyMemberId);
        studyMemberById.ModifyStudyRole(SELECTED);
    }


    public void deleteMember(Long studyMemberId, Long studyId,  String userId) throws AccessDeniedException {

        validateUserAccess(studyId, userId);

        if (!jpaStudyMemberDao.existsById(studyMemberId)) {
            throw new MemberNotExistException();
        }
        jpaStudyMemberDao.deleteById(studyMemberId);
    }
}
