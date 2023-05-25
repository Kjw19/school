package sm.school.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.PersonalInf;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PersonalInfRequestDto {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "생년월일은 필수입니다.")
    private String birth;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String phone;

    @Email(message = "이메일 형식으로 작성해주세요.")
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;

    @Builder
    public PersonalInfRequestDto(String name, String birth, String phone, String email) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
    }
    //PersonalInfDTO를 PersonalInf로 변환하는 메서드
    public PersonalInf toEntity() {
        return PersonalInf.builder()
                .name(name)
                .birth(birth)
                .phone(phone)
                .build();
    }
}
