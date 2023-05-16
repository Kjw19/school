package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Repository.member.MemberRepository;
import sm.school.Service.commonConst.DefaultProfileUrl;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.FileSizeException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.dao.member.JpaMemberDao;
import sm.school.domain.member.Member;
import sm.school.dto.member.ExistingUser;
import sm.school.dto.member.MemberDTO;
import sm.school.dto.member.NewUser;

import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;
import static sm.school.Service.commonConst.Status.NOT_SELECTED;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final JpaMemberDao jpaMemberDao;


    private final PasswordEncoder passwordEncoder;

    private final CommonService commonService;



    //회원가입
    public Member signUp(@Validated(NewUser.class) MemberDTO memberDTO, MultipartFile profileImage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotFoundException();
        }
        //프로필 사진 업로드
        updateProfileImg(memberDTO, profileImage);


        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));//패스워드 인코딩진행
        Member memberSave = memberDTO.toMemberEntity();//MemberDTO -> 엔티티로 변환


        return jpaMemberDao.save(memberSave);
    }

    //회원 수정
    public void modifyMember(@Validated(ExistingUser.class) MemberDTO memberDTO, MultipartFile profileImage,
                             BindingResult bindingResult, String userId) {
        if (bindingResult.hasErrors()) {
            throw new DataNotFoundException();
        }
        Member member = jpaMemberDao.findMemberByUserId(userId);

        updateProfileImg(memberDTO, profileImage);
        member.updateMember(memberDTO.getSchool(), memberDTO.getMajor(), memberDTO.getProfile(),
                memberDTO.getPersonalInfDTO().toPersonalInf(), memberDTO.getAddressDTO().toAddress());
    }

    public boolean checkUserIdDuplicate(String userId) {
        return jpaMemberDao.existsByUserId(userId);
    }

    //회원 정보 받아오기
    public List<MemberDTO> memberList() {
        return jpaMemberDao.findAll().stream()
                .map(Member::toMemberDTO)
                .collect(Collectors.toList());
    }

    public void changeRole(Long id) {
        Member member = jpaMemberDao.findMemberById(id);
        log.info("memberRole {}",member.getRole());
        if (member.getRole().equals(NOT_SELECTED)) {
            member.changeRole(NOT_SELECTED);
        } else {
            member.changeRole(SELECTED);
        }
    }

    public MemberDTO  findMember(String userId) {

        if (!jpaMemberDao.existsByUserId(userId)) {
            throw new MemberNotExistException();
        }

        Member member = jpaMemberDao.findMemberByUserId(userId);
        MemberDTO memberDTO = member.toMemberDTO();

        return memberDTO;
    }

    public void updateProfileImg(MemberDTO memberDTO, MultipartFile multipartFile) {
        //프로필 사진 업로드
        if (multipartFile.isEmpty()) {
            memberDTO.setProfile(DefaultProfileUrl.Url);
        } else {
            if (multipartFile.getSize() > 5000000){//5MB
                throw new FileSizeException();
            }
            String profileImageUrl = commonService.uploadFileToS3(multipartFile);
            memberDTO.setProfile(profileImageUrl);
        }
    }

    public String getCurrentProfile(String userId) {

        Member member = jpaMemberDao.findMemberByUserId(userId);
        MemberDTO memberDTO = member.toMemberDTO();

        return memberDTO.getProfile();
    }
}
