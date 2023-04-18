package sm.school.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //Getter 및 생성자로만 접근 가능하도록 설정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memId")
    private Long id;//기본값

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    @Column(unique = true)
    private String userId;//유저 아이디

    @NotEmpty
    private String passwd;//비밀번호
    @NotEmpty
    private String school;//학교
    @NotEmpty
    private String major;//전공
    private String mem_profile;//회원 이미지


    @Column(name = "memRole")
    @NotNull
    @ColumnDefault("1")//1: 일반회원, 2:정지회원, 3:탈퇴회원, 9:관리자
    private Integer role;

    @Column(name = "memReg")
    @CreationTimestamp
    private Date date; //가입일자

    @Embedded
    private PersonalInf personalInf = new PersonalInf();//이름 생년월일 전화번호 내장

    @Embedded
    private Address address = new Address(); //우편번호, 주소, 상세 주소 내장

    //내장형 접근용 setter(사용하지않으면 접근이 불가능하다 왜지?)
    public void setPersonalInf(PersonalInf personalInf) {
        this.personalInf = personalInf;
    }
    public void setAddress(Address address) {
        this.address = address;
    }



    @Builder //회원가입 생성자
    public Member(Long id, String userId, String passwd, String school,
                  String major, String mem_profile, Integer role, Date date, PersonalInf personalInf,
                  Address address) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.school = school;
        this.major = major;
        this.role = (role == null) ? 1 : role;;
        this.date = date;
        this.mem_profile = mem_profile;
        this.personalInf = personalInf;
        this.address = address;
    }

    //회원수정 메서드
    public void updateMember(String userId, String school, String major,
                  String mem_profile, PersonalInf personalInf,
                  Address address) {
        this.userId = userId;
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
