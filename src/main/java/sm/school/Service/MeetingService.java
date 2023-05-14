package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.dao.meeting.JpaMeetingDao;
import sm.school.dao.meeting.JpaMeetingProposerDao;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.meeting.MeetingProposer;
import sm.school.dto.meeting.MeetingDTO;
import sm.school.dto.meeting.MeetingProposerDTO;
import sm.school.dto.meeting.MeetingProposerDetailDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

    private final JpaMeetingDao jpaMeetingDao;
    private final JpaMeetingProposerDao jpaMeetingProposerDao;
    private final CommonService commonService;

    public static final String Meeting_Not_Exist = "미팅이 존재하지 않습니다";

    //글쓴이 인지 확인하는 검증 로직
    @Transactional(readOnly = true)
    public void validateUserAccess(Long meetId, String userId) throws AccessDeniedException {
        MeetingDTO selectMeeting = detailMeeting(meetId);
        if (!userId.equals(selectMeeting.getMember().getUserId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    public Meeting createMeeting(MeetingDTO meetingDTO, Authentication authentication) {

        //사용자 정보를 받아 memberDetails로 저장
        meetingDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Meeting meeting = meetingDTO.toMeetingEntity();

        return jpaMeetingDao.save(meeting);
    }

    //meetingDTO와 meetingProposerDTOList값을 반환
    @Transactional(readOnly = true)
    public MeetingProposerDetailDTO getMeetingProposerDetail(Long meetId, String userId) throws AccessDeniedException {
        validateUserAccess(meetId, userId);
        //검증로직 끝
        List<MeetingProposerDTO> meetingProposerDTOS = selectMeetingProposer(meetId);
        MeetingDTO selectMeeting = detailMeeting(meetId);
        //validateUserAccess 에서 한번 호출했지만 한번더 호출함
        //성능적인 부하가 높지 않다 생각하고
        // validateUserAccess 를 검증 로직으로만 사용하는것이 옮다고 생각하여 작성

        return new MeetingProposerDetailDTO(selectMeeting, meetingProposerDTOS);
    }

    //참여자 선택 서비스 로직
    public void approvalProposer(Long proposerId, Long meetId, String userId) throws AccessDeniedException {

        validateUserAccess(meetId, userId);//검증
        selectProposer(proposerId, meetId);//선택
        completeMeeting(meetId);//미팅 마감
        afterDeleteToSelect(meetId);//선택받지 못한 참가자 제거
    }

    //참여자 삭제 서비스 로직
    public void deleteProposerIfAuthorized(Long proposerId, Long meetId, String userId) throws AccessDeniedException {

        validateUserAccess(meetId, userId);//검증 로직

        deleteProposer(proposerId);//회원삭제로직
    }


    //참가자 삭제
    public void deleteProposer(Long id) {
        //데이터가 존재하는지 체크
        if (!jpaMeetingProposerDao.existsById(id)) {
            throw new MemberNotExistException();
        }
        jpaMeetingProposerDao.deleteById(id);
    }


    public void updateMeeting(MeetingDTO meetingDTO) {

      Meeting meeting = jpaMeetingDao.findMeetingById(meetingDTO.getId());

            meeting.modifyMeeting(meetingDTO.getTitle(), meetingDTO.getIntroduction(),
                    meetingDTO.getSchool(), meetingDTO.getMajor(), meetingDTO.getRegion(),
                    meetingDTO.getCount());
    }

    @Transactional(readOnly = true)
    public List<MeetingDTO> findMeeting() {

        //반복문for 대신 stream사용했으며  map을 통해 toMeetingDTO를 실행하며 Meeting을 MeetingDTO 객체로 변환한다.
        //collect를 통해 MeetingDTO객체들을 다시 List로 변환하며
        //Collectors.toList()를 통해 collect()가 List를 반환하도록 한다.
        return jpaMeetingDao.findAll().stream()
                .map(Meeting::toMeetingDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MeetingDTO detailMeeting(Long id) {
        return jpaMeetingDao.findById(id)
                .map(Meeting::toMeetingDTO)
                .orElseThrow(() -> new RuntimeException(Meeting_Not_Exist));
    }

    //미팅 삭제
    public void deleteMeeting(Long id, String userId) throws AccessDeniedException {

        validateUserAccess(id, userId); //검증

        //데이터가 존재하는지 체크
        if (!jpaMeetingDao.existsById(id)) {
            throw new DataNotFoundException();
        }
            //미팅 회원 목록 가져오기
            List<MeetingProposer> meetingProposerList = jpaMeetingProposerDao.findByMeetingsId(id);

            //미팅 회원 목록 삭제
            //데이터베이스와 관련된 작업은 stream보다는 for문으로 처리
            //stream을 이용하면 데이터베이스에 대한 삭제 작업이 별도의 트랜잭션으로 간주될 수 있어
            //롤백이 되지않음
            for (MeetingProposer meetingProposer: meetingProposerList) {
                jpaMeetingProposerDao.deleteById(meetingProposer.getId());
            }
            //미팅 삭제
            jpaMeetingDao.deleteById(id);
    }

    //미팅 완료상태 변경
    public void completeMeeting(Long meetId) {
        Meeting meeting = jpaMeetingDao.findMeetingById(meetId);

        meeting.changeStatus(SELECTED);
    }

    //MeetingProposer

    public MeetingProposer createMeetingProposer(MeetingProposerDTO meetingProposerDTO,
                                                 Authentication authentication, Long id) {
        MeetingDTO meetingDTO = detailMeeting(id);

        meetingProposerDTO.setMember(commonService.getMemberFromAuthentication(authentication));
        meetingProposerDTO.setMeetings(meetingDTO.toMeetingEntity());

        MeetingProposer meetingProposer = meetingProposerDTO.toMeetingProposer();

        return jpaMeetingProposerDao.save(meetingProposer);
    }

    @Transactional(readOnly = true)
    public List<MeetingProposerDTO> selectMeetingProposer(Long id) {

        List<MeetingProposer> meetingProposerList = jpaMeetingProposerDao.findByMeetingsId(id);

        return meetingProposerList.stream()
                .map(MeetingProposer::toMeetingProposerDTO)
                .collect(Collectors.toList());
    }

    //참가자 선택
    public void selectProposer(Long id, Long meetId) {

        MeetingProposer meetingProposer = jpaMeetingProposerDao.findById(id)
                .orElseThrow(MemberNotExistException::new);
        meetingProposer.changeStatus(SELECTED);
    }

    //선택후 나머지 선택받지 못한 참가자 삭제
    public void afterDeleteToSelect(Long meetId) {
        List<MeetingProposer> meetingsIdAndStatus = jpaMeetingProposerDao.findByMeetingsIdAndStatus(meetId, 0);
        meetingsIdAndStatus.forEach(meetingProposer -> jpaMeetingProposerDao.deleteById(meetingProposer.getId()));
    }

}
