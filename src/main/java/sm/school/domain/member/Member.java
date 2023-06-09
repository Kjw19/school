package sm.school.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.dto.member.MemberDTO;

import javax.persistence.*;
import java.util.Date;

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


    @Column(nullable = false)
    private String passwd;//비밀번호

    @Column(nullable = false)
    private String school;//학교

    private String major;//전공

    @Column(name = "mem_profile")
    private String profile;//회원 이미지


    @Column(name = "memRole",nullable = false)
    @ColumnDefault("1")//0: 정지회원, 1:일반 회원
    private Integer role;

    @Column(name = "memReg")
    @CreationTimestamp
    private Date date; //가입일자

    @Embedded
    private PersonalInf personalInf;//이름 생년월일 전화번호 내장

    @Embedded
    private Address address; //우편번호, 주소, 상세 주소 내장

    @Builder //회원가입 생성자
    public Member(Long id, String userId, String passwd, String school,
                  String major, String profile, Integer role, Date date, PersonalInf personalInf,
                  Address address) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.school = school;
        this.major = major;
        this.role = (role == null) ? 1 : role;
        this.date = date;
        this.profile = profile;
        this.personalInf = personalInf;
        this.address = address;
    }

    //회원수정 메서드
    public void updateMember(String school, String major,
                  String profile, PersonalInf personalInf,
                  Address address) {
        this.school = school;
        this.major = major;
        this.profile = profile;
        this.personalInf = personalInf;
        this.address = address;
    }


    //비밀번호 변경 메서드
    public void updatePasswd(String passwd) {
        this.passwd = passwd;
    }

    public void changeRole(int role) {
        this.role = role;
    }

    public MemberDTO toMemberDTO() {
        return MemberDTO.builder()
                .id(id)
                .userId(userId)
                .passwd(passwd)
                .school(school)
                .major(major)
                .date(date)
                .role(role)
                .profile(profile)
                .personalInfDTO(personalInf.toPersonalInfDTO())
                .addressDTO(address.toAddressDTO())
                .build();
    }
}
