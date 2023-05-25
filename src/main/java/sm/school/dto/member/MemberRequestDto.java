package sm.school.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;
import sm.school.domain.member.MemberDetail;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    private Long id;
    @NotEmpty(message = "아이디는 필수입니다.", groups = NewUser.class)
    private String userId;
    @NotEmpty(message = "비밀번호는 필수입니다.", groups = NewUser.class)
    private String passwd;

    private Integer role = 1;

    private MemberDetail memberDetail;

    private String oauthProvider; //소셜 로그인 제공자(구글, 카카오)

    private String oauthProviderId; //소셜 로그인 아이디

    @Builder
    public MemberRequestDto(Long id, String userId, String passwd, Integer role, MemberDetail memberDetail, String oauthProvider, String oauthProviderId) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.role = role;
        this.memberDetail = memberDetail;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }

    //MemberDTO -> Member 변환
    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .userId(this.userId)
                .passwd(this.passwd)
                .role(this.role)
                .memberDetail(this.memberDetail)
                .oauthProvider(this.oauthProvider)
                .oauthProviderId(this.oauthProviderId)
                .build();
    }
}
