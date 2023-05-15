package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Repository.member.MemberRepository;
import sm.school.Service.commonError.DataNotFoundException;
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
    private final S3Client s3Client;


    //회원가입
    public Member signUp(MemberDTO memberDTO, MultipartFile profileImage, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataNotFoundException();
        }
        String profileImageUrl = uploadFileToS3(profileImage);
        memberDTO.setProfile(profileImageUrl);

        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));//패스워드 인코딩진행
        Member memberSave = memberDTO.toMemberEntity();//MemberDTO -> 엔티티로 변환

        return memberRepository.save(memberSave);
    }

    public MemberDTO findMember(String userId) {
        Member member = memberRepository.findMemberByUserId(userId);
        MemberDTO memberDTO = member.toMemberDTO();

        return memberDTO;
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

    private String uploadFileToS3(MultipartFile file) {
        String bucketName = "schoolpro-s3-bucket";
        String key = file.getOriginalFilename();

        try{
            InputStream inputStream = file.getInputStream();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toString();

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateProfileImage(Long id, MultipartFile file) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotExistException());

        String imageUrl = uploadFileToS3(file);

        return imageUrl;
    }
}
