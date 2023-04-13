package sm.school.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import sm.school.domain.embeded.Address;
import sm.school.domain.embeded.PersonalInf;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //Getter 및 생성자로만 접근 가능하도록 설정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_id")
    private Long id;//기본값

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    @Column(unique = true)
    private String user_id;//유저 아이디

    @NotEmpty
    private String passwd;//비밀번호
    @NotEmpty
    private String school;//학교
    @NotEmpty
    private String major;//전공
    @NotEmpty
    private String mem_profile;//회원 이미지

    @NotEmpty
    @Column(name = "mem_role")
    @ColumnDefault("1")//1: 일반회원, 2:정지회원, 3:탈퇴회원, 9:관리자
    private int role;

    @Embedded
    private PersonalInf personalInf;//이름 생년월일 전화번호 내장

    @Embedded
    private Address address; //우편번호, 주소, 상세 주소 내장


    @Builder //회원가입 생성자
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

    //회원수정 메서드
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


    //비밀번호 변경 메서드
    public void updatePasswd(String passwd) {
        this.passwd = passwd;
    }
}
