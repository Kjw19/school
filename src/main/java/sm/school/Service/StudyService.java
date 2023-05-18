package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.FileSizeException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.dao.study.JpaStudyDao;
import sm.school.dao.study.JpaStudyMemberDao;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.study.Study;
import sm.school.domain.study.StudyMember;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;
import sm.school.dto.meeting.MeetingDTO;

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
        }
    }

    public Study createStudy(StudyDTO studyDTO, MultipartFile imageFile, Authentication authentication) {

        updateProfile(studyDTO, imageFile);


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

    public void updateStudy(StudyDTO studyDTO, String userId,
                            MultipartFile multipartFile) throws AccessDeniedException {

        validateUserAccess(studyDTO.getId(), userId);
        Study study = jpaStudyDao.findStudyById(studyDTO.getId());
        studyDTO.setProfile(study.getProfile());
        updateProfile(studyDTO, multipartFile);

        study.UpdateStudy(studyDTO.getName(), studyDTO.getContent(),studyDTO.getRegion(), studyDTO.getProfile(),studyDTO.getRegType());
    }

    public String currentProfile(Long id) {
        Study study = jpaStudyDao.findStudyById(id);
        StudyDTO studyDTO = study.toStudyDTO();
        return studyDTO.getProfile();
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

    public void updateProfile(StudyDTO studyDTO, MultipartFile multipartFile) {
        String upload = commonService.processUpload(multipartFile);
        if (upload != null) {
            studyDTO.setProfile(upload);
        }
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

    public String currentImage(Long id) {

        Study study = jpaStudyDao.findStudyById(id);
        StudyDTO studyDTO = study.toStudyDTO();
        return studyDTO.getProfile();
    }

    public List<StudyDTO> findStudyFromUser(String memId) {
        return jpaStudyDao.findStudyByMemberUserId(memId).stream()
                .map(Study::toStudyDTO)
                .collect(Collectors.toList());
    }
}
