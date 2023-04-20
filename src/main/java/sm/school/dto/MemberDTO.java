package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String passwd;
    @NotEmpty(message = "학교는 필수입니다,")
    private String school;
    private String major;

    private String mem_profile;
    private Integer role = 1;
    private Date date;

    @Valid
    private PersonalInfDTO personalInfDTO;

    @Valid
    private AddressDTO addressDTO;


    @Builder
    public MemberDTO(Long id, String userId, String passwd, String school,
                     String major, String mem_profile, Integer role, Date date,
                     PersonalInfDTO personalInfDTO, AddressDTO addressDTO) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.school = school;
        this.major = major;
        this.mem_profile = mem_profile;
        this.role = role;
        this.date = date;
        this.personalInfDTO = personalInfDTO;
        this.addressDTO = addressDTO;
    }

    //MemberDTO -> Member 변환
    public Member toMemberEntity() {
        return Member.builder()
                .id(this.id)
                .userId(this.userId)
                .passwd(this.passwd)
                .school(this.school)
                .major(this.major)
                .mem_profile(this.mem_profile)
                .role(this.role)
                .date(this.date)
                .personalInf(this.personalInfDTO.toPersonalInf())
                .address(this.addressDTO.toAddress()).build();
    }
}
