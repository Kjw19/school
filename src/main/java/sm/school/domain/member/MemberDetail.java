package sm.school.domain.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailId")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;//유저 아이디

    @Column(nullable = false)
    private String school;//학교

    private String major;//전공

    @Column(name = "mem_profile")
    private String profile;//회원 이미지

    @Column(name = "memReg")
    @CreatedDate
    private LocalDate date; //가입일자

    @Embedded
    private PersonalInf personalInf;//이름 생년월일 전화번호 내장

    @Embedded
    private Address address; //우편번호, 주소, 상세 주소 내장

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memId")
    private Member member;

    @Builder
    public MemberDetail(Long id, String nickname, String school, String major, String profile, Integer role, LocalDate date,
                        PersonalInf personalInf, Address address, Member member) {
        this.id = id;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.profile = profile;
        this.date = date;
        this.personalInf = personalInf;
        this.address = address;
        this.member = member;
    }

    //회원상세정보 수정 메서드
    public void updateMemberDetail(String school, String major, String profile,
                        PersonalInf personalInf, Address address) {
        this.school = school;
        this.major = major;
        this.profile = profile;
        this.personalInf = personalInf;
        this.address = address;
    }
}
