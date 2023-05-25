package sm.school.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //Getter 및 생성자로만 접근 가능하도록 설정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memId")
    private Long id;//기본값

    @Column(unique = true, nullable = false)
    private String userId;//유저 아이디


    private String passwd;//비밀번호

    @Column(name = "memRole",nullable = false)
    @ColumnDefault("1")//0: 정지회원, 1:일반 회원
    private Integer role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detailId")
    private MemberDetail memberDetail;

    private String oauthProvider; //소셜 로그인 제공자(구글, 카카오)

    private String oauthProviderId; //소셜 로그인 아이디

    @Builder
    public Member(Long id, String userId, String passwd, Integer role,
                  MemberDetail memberDetail, String oauthProvider, String oauthProviderId) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.role = role;
        this.memberDetail = memberDetail;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }

    //비밀번호 변경 메서드
    public void updatePasswd(String passwd) {
        this.passwd = passwd;
    }

    public void changeRole(int role) {
        this.role = role;
    }

}
