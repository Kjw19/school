package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sm.school.domain.member.Member;
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


    public Member getMemberFromAuthentication(Authentication authentication) {
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        return memberDetails.getMember();
    }


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

}
