package sm.school.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.embeded.Address;
import sm.school.domain.embeded.PersonalInf;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_id")
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String user_id;

    @NotEmpty
    private String passwd;
    @NotEmpty
    private String school;
    @NotEmpty
    private String major;
    @NotEmpty
    private String mem_profile;//회원 이미지

    @Embedded
    private PersonalInf personalInf;//이름 생년월일 전화번호 내장

    @Embedded
    private Address address; //우편번호, 주소, 상세 주소 내장


    @Builder
    public Member(Long id, String user_id, String passwd, String school,
                  String major, String mem_profile, PersonalInf personalInf,
                  Address address) {
        this.id = id;
        this.user_id = user_id;
        this.passwd = passwd;
        this.school = school;
        this.major = major;
        this.mem_profile = mem_profile;
        this.personalInf = personalInf;
        this.address = address;
    }

    public void updateMember(String user_id, String school, String major,
                  String mem_profile, PersonalInf personalInf,
                  Address address) {
        this.user_id = user_id;
        this.school = school;
        this.major = major;
        this.mem_profile = mem_profile;
        this.personalInf = personalInf;
        this.address = address;
    }


    public void updatePasswd(String passwd) {
        this.passwd = passwd;
    }
}
