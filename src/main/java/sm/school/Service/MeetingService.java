package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.MeetingProposerRepository;
import sm.school.Repository.MeetingRepository;
import sm.school.domain.meeting.Meeting;
import sm.school.domain.meeting.MeetingProposer;
import sm.school.domain.member.Member;
import sm.school.dto.meeting.MeetingDTO;
import sm.school.dto.meeting.MeetingProposerDTO;
import sm.school.dto.meeting.MeetingProposerDetailDTO;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingProposerRepository meetingProposerRepository;
    private final CommonService commonService;

    public Meeting createMeeting(MeetingDTO meetingDTO, Authentication authentication) {

        //사용자 정보를 받아 memberDetails로 저장
        meetingDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        Meeting meeting = meetingDTO.toMeetingEntity();

        return meetingRepository.save(meeting);
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
    public void ProposerSelect(Long proposerId, Long meetId, String userId) throws AccessDeniedException {

        validateUserAccess(meetId, userId);

        selectProposer(proposerId, meetId);
        completeMeeting(meetId);
        if (!afterDeleteToSelect(meetId)) {
            throw new RuntimeException("삭제 실패");
        }
    }

    //참여자 삭제 서비스 로직
    public void ProposerDelete(Long proposerId, Long meetId, String userId) throws AccessDeniedException {

        validateUserAccess(meetId, userId);

        Boolean deleteProposer = deleteProposer(proposerId);
        if (!deleteProposer) {
            throw new RuntimeException("삭제 실패");
        }
    }

    //글쓴이 인지 확인하는 검증 로직
    @Transactional(readOnly = true)
    public void validateUserAccess(Long meetId, String userId) throws AccessDeniedException {
        MeetingDTO selectMeeting = detailMeeting(meetId);
        if (!userId.equals(selectMeeting.getMember().getUserId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    public void updateMeeting(MeetingDTO meetingDTO) {

        Optional<Meeting> meetingOptional = meetingRepository.findMeetingById(meetingDTO.getId());

        meetingOptional.ifPresent(meeting -> {
            meeting.modifyMeeting(meetingDTO.getTitle(), meetingDTO.getIntroduction(),
                    meetingDTO.getSchool(), meetingDTO.getMajor(), meetingDTO.getRegion(),
                    meetingDTO.getCount());
        });
    }

    @Transactional(readOnly = true)
    public List<MeetingDTO> findMeeting() {

        List<Meeting> meetingList = meetingRepository.findAll();
        List<MeetingDTO> meetingDTOList = new ArrayList<>();
        for (Meeting meeting: meetingList) {
            meetingDTOList.add(meeting.toMeetingDTO());
        }
        return meetingDTOList;
    }

    @Transactional(readOnly = true)
    public MeetingDTO detailMeeting(Long id) {

        Optional<Meeting> meetingOptional = meetingRepository.findMeetingById(id);
        MeetingDTO meetingDTO = null;
        if (meetingOptional.isPresent()) {
            Meeting meeting = meetingOptional.get();
            meetingDTO = meeting.toMeetingDTO();
        } else {
            throw new RuntimeException("미팅이 존재하지 않습니다");
        }

        return meetingDTO;
    }

    public Boolean DeleteMeeting(Long id) {

        try {
            //미팅 회원 목록 가져오기
            List<MeetingProposer> meetingProposerList = meetingProposerRepository.findByMeetingsId(id);

            //미팅 회원 목록 삭제
            for (MeetingProposer meetingProposer: meetingProposerList) {
                meetingProposerRepository.deleteById(meetingProposer.getId());
            }
            //미팅 삭제
            meetingRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            //삭제하려는 미팅이 이미 존재하지 않을 때
            return false;
        }
    }

    //미팅 완료상태 변경
    public void completeMeeting(Long meetId) {
        Optional<Meeting> meetingOptional = meetingRepository.findMeetingById(meetId);
        Meeting meeting = null;
        if (meetingOptional.isPresent()) {
            meeting = meetingOptional.get();
            meeting.changeStatus(1);
        } else {
            throw new RuntimeException("미팅이 존재하지 않습니다");
        }
    }

    //MeetingProposer

    public MeetingProposer createMeetingProposer(MeetingProposerDTO meetingProposerDTO,
                                                 Authentication authentication, Long id) {

        meetingProposerDTO.setMember(commonService.getMemberFromAuthentication(authentication));

        MeetingDTO meetingDTO = detailMeeting(id);

        meetingProposerDTO.setMeetings(meetingDTO.toMeetingEntity());

        MeetingProposer meetingProposer = meetingProposerDTO.toMeetingProposer();

        return meetingProposerRepository.save(meetingProposer);
    }

    @Transactional(readOnly = true)
    public List<MeetingProposerDTO> selectMeetingProposer(Long id) {

        List<MeetingProposer> meetingProposerList = meetingProposerRepository.findByMeetingsId(id);
        List<MeetingProposerDTO> meetingProposerDTOList = new ArrayList<>();

        for (MeetingProposer meetingProposer: meetingProposerList) {
            meetingProposerDTOList.add(meetingProposer.toMeetingProposerDTO());
        }
        return meetingProposerDTOList;
    }

    //참가자 선택
    public void selectProposer(Long id, Long meetId) {

        Optional<MeetingProposer> meetingProposerOptional = meetingProposerRepository.findById(id);
        MeetingProposer meetingProposer = null;

        if (meetingProposerOptional.isPresent()) {
            meetingProposer = meetingProposerOptional.get();
        } else {
                throw new RuntimeException("미팅 회원이 존재하지 않습니다");
        }
        meetingProposer.changeStatus(1);
    }

    //선택후 나머지 선택받지 못한 참가자 삭제
    public Boolean afterDeleteToSelect(Long meetId) {
        List<MeetingProposer> byMeetingsIds = meetingProposerRepository.findByMeetingsId(meetId);
        try {
            for (MeetingProposer byMeetingsId : byMeetingsIds) {
                if (byMeetingsId.getStatus() == 0) {
                    meetingProposerRepository.deleteById(byMeetingsId.getId());
                }
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    //참가자 삭제
    public Boolean deleteProposer(Long id) {
        try {
            meetingProposerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
