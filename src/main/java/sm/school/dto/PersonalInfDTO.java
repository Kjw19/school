package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.PersonalInf;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PersonalInfDTO {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "생년월일은 필수입니다.")
    private String birth;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String phone;


    //PersonalInf를 PersonalInfDTO로 변환하는 생성자
    @Builder
    public PersonalInfDTO(PersonalInf personalInf) {
        this.name = personalInf.getName();
        this.birth = personalInf.getBirth();
        this.phone = personalInf.getPhone();
    }

    //PersonalInfDTO를 PersonalInf로 변환하는 메서드
    public PersonalInf toPersonalInf() {
        return PersonalInf.builder()
                .name(name)
                .birth(birth)
                .phone(phone)
                .build();
    }
}
