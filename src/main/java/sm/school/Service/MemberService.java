package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Repository.member.MemberRepository;
import sm.school.Service.commonConst.DefaultProfileUrl;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.Service.commonError.FileSizeException;
import sm.school.Service.commonError.MemberNotExistException;
import sm.school.domain.member.Member;
import sm.school.dto.MemberDTO;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;
import static sm.school.Service.commonConst.Status.NOT_SELECTED;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberSecurityService memberSecurityService;

    private final PasswordEncoder passwordEncoder;

    private final CommonService commonService;

    private final S3Client s3Client;


    //회원가입
    public Member signUp(MemberDTO memberDTO, MultipartFile profileImage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotFoundException();
        }
        //프로필 사진 업로드
        if (profileImage.isEmpty()) {
            memberDTO.setProfile(DefaultProfileUrl.Url);
        } else {
            if (profileImage.getSize() > 5000000){//5MB
                throw new FileSizeException();
            }
            String profileImageUrl = commonService.uploadFileToS3(profileImage);
            memberDTO.setProfile(profileImageUrl);
        }

        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));//패스워드 인코딩진행
        Member memberSave = memberDTO.toMemberEntity();//MemberDTO -> 엔티티로 변환

        return memberRepository.save(memberSave);
    }

    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    //회원 정보 받아오기
    public List<MemberDTO> memberList() {
        return memberRepository.findAll().stream()
                .map(Member::toMemberDTO)
                .collect(Collectors.toList());
    }

    public void changeRole(Long id) {
        Member member = memberRepository.findMemberById(id);
        log.info("memberRole {}",member.getRole());
        if (member.getRole().equals(NOT_SELECTED)) {
            member.changeRole(NOT_SELECTED);
        } else {
            member.changeRole(SELECTED);
        }
    }
}
