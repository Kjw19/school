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
public class PersonalInfResponseDto {

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
    public PersonalInfResponseDto(String name, String birth, String phone, String email) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
    }

    public static PersonalInfResponseDto toDto(PersonalInf personalInf) {
        return PersonalInfResponseDto.builder()
                .name(personalInf.getName())
                .birth(personalInf.getBirth())
                .phone(personalInf.getPhone())
                .email(personalInf.getEmail())
                .build();
    }
}
