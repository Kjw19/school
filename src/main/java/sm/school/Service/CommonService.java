package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.commonError.FileSizeException;
import sm.school.domain.member.Member;
import sm.school.dto.FileUploadDTO;
import sm.school.dto.meeting.MeetingDTO;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonService {
    private final S3Client s3Client;

    //회원의 세션정보를 통해 사용자의 정보를 가져옴
    public Member getMemberFromAuthentication(Authentication authentication) {
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        return memberDetails.getMember();
    }

    //업로드 로직
    protected String uploadFileToS3(MultipartFile file) {
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

    //업로드실행로직 및 공통 검증
    public String processUpload(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            if (multipartFile.getSize() > 5000000) {
                throw new FileSizeException();
            }
            return uploadFileToS3(multipartFile);
        }
        return null;
    }


}
