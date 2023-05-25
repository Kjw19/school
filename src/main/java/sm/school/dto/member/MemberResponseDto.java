package sm.school.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;
import sm.school.domain.member.MemberDetail;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String userId;
    private String passwd;
    private Integer role;
    private MemberDetail memberDetail;

    private String oauthProvider; //소셜 로그인 제공자(구글, 카카오)

    private String oauthProviderId; //소셜 로그인 아이디

    @Builder
    public MemberResponseDto(Long id, String userId, String passwd, Integer role, MemberDetail memberDetail,
                             String oauthProvider, String oauthProviderId) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.role = role;
        this.memberDetail = memberDetail;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }

    //Member 엔티티를 받아 MemberResponseDTO로 변환
    public static MemberResponseDto toDTO(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .passwd(member.getPasswd())
                .role(member.getRole())
                .memberDetail(member.getMemberDetail())
                .oauthProvider(member.getOauthProvider())
                .oauthProviderId(member.getOauthProviderId())
                .build();
    }
}
