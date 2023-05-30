package sm.school.Service.Contest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Repository.contest.ContestMemberRepository;
import sm.school.Repository.contest.ContestRepository;
import sm.school.Service.CommonService;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.dao.contest.JpaContestDAO;
import sm.school.dao.contest.JpaContestMemberDAO;
import sm.school.dao.study.JpaStudyDao;
import sm.school.domain.contest.Contest;
import sm.school.domain.contest.ContestDTO;
import sm.school.domain.contest.ContestMember;
import sm.school.domain.contest.ContestMemberDTO;
import sm.school.domain.study.Study;
import sm.school.domain.study.StudyMember;
import sm.school.dto.StudyDTO;
import sm.school.dto.StudyMemberDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;

@Service
@RequiredArgsConstructor//생성자 주입 autowire 보다 장점이 많다(final 사용가능)
@Transactional
@Slf4j

public class ContestService {
    private final CommonService commonService;

    private final JpaContestDAO jpaContestDAO;

    private final JpaContestMemberDAO jpaContestMemberDAO;


    public void validateUserAccess(Long contestId, String userId) throws AccessDeniedException {
        ContestDTO contestDTO = selectContest(contestId);
        if (!userId.equals(contestDTO.getMember().getUserId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    public Contest creatContest(ContestDTO contestDTO, MultipartFile imageFile, Authentication authentication){
        updateProfile(contestDTO, imageFile);


        contestDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Contest contest = contestDTO.toContestEntity();//DTO->entity로 변환

        return jpaContestDAO.save(contest);

    }
    public boolean checkStudyNameDuplicate(String name) {
        log.info("name {}", name);
        return jpaContestDAO.existsByConName(name);
    }
    @Transactional(readOnly = true)
    public List<ContestDTO>ListContest(){
        return jpaContestDAO.findAll().stream()
                .map(Contest::toContestDTO)
                .collect(Collectors.toList());
    }

    public ContestDTO selectContest(Long contestId){
        Contest contest = jpaContestDAO.findContestById(contestId);
        ContestDTO contestDTO = contest.toContestDTO();

        return contestDTO;

    }

    public void updateContest(ContestDTO contestDTO,String userId,MultipartFile imageFile)throws AccessDeniedException{
        validateUserAccess(contestDTO.getId(),userId);
        Contest contest = jpaContestDAO.findContestById(contestDTO.getId());
        contestDTO.setConPicture(contest.getConPicture());
        updateProfile(contestDTO, imageFile);
        contest.ModifyContest(contestDTO.getConName(), contestDTO.getConInf(), contestDTO.getConPicture(),contestDTO.getRegType());

    }
    public String currentProfile(Long id) {
        Contest contest = jpaContestDAO.findContestById(id);
        ContestDTO contestDTO = contest.toContestDTO();
        return contestDTO.getConPicture();
    }


    public void deleteContest(Long id, String userId) throws AccessDeniedException {

        validateUserAccess(id, userId);

        if (!jpaContestDAO.existsById(id)) {
            throw new DataNotFoundException();
        }
        List<ContestMember> contestMemberList = jpaContestMemberDAO.findByContestId(id);

        for (ContestMember contestMember: contestMemberList) {
            jpaContestDAO.deleteById(contestMember.getId());
        }
        jpaContestDAO.deleteById(id);
    }

    public void updateProfile(ContestDTO contestDTO, MultipartFile multipartFile) {
        String upload = commonService.processUpload(multipartFile);
        if (upload != null) {
            contestDTO.setConPicture(upload);
        }
    }
    //대회 참가 멤버

    public ContestMember createContestMember(Long id,
                                         ContestMemberDTO contestMemberDTO,
                                         Authentication authentication) {
        log.info("role {}", contestMemberDTO.getRole());
        log.info("회원생성시작");
        ContestDTO contestDTO = selectContest(id);


        contestMemberDTO.setContest(contestDTO.toContestEntity());
        contestMemberDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        ContestMember contestMember = contestMemberDTO.toContestMemberEntity();



        return jpaContestMemberDAO.save(contestMember);

    }
    public List<ContestMemberDTO> selectContestMemberList(Long contestId, String userId) throws AccessDeniedException {//대회참가 멤버 선택

        validateUserAccess(contestId, userId);

        return jpaContestMemberDAO.findByContestId(contestId).stream()
                .map(ContestMember :: toContestMemberDTO)
                .collect(Collectors.toList());
    }
    public void accessMember(Long contestMemberId, String userId) {
        ContestMember contestMemberById = jpaContestMemberDAO.findContestMemberById(contestMemberId);
        contestMemberById.ModifyContestRole(SELECTED);

    }


    public void deleteMember(Long contestMemberId, Long contestId,  String userId) throws AccessDeniedException {

        validateUserAccess(contestId, userId);

        if (!jpaContestMemberDAO.existsById(contestMemberId)) {
            throw new MemberNotExistException();
        }
        jpaContestMemberDAO.deleteById(contestMemberId);
    }
    public String currentImage(Long id) {

        Contest contest = jpaContestDAO.findContestById(id);
        ContestDTO contestDTO = contest.toContestDTO();
        return contestDTO.getConPicture();
    }
    public List<ContestDTO> findContestFromUser(String memId) {
        return jpaContestDAO.findContestByMemberUserId(memId).stream()
                .map(Contest::toContestDTO)
                .collect(Collectors.toList());
    }


}
