package sm.school.domain.member;

import lombok.*;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@NoArgsConstructor
public class PersonalInf {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "생년월일은 필수입니다.")
    private String birth;

    @NotEmpty(message = "전화번호는 필수입니다.")
    private String phone;

    //개인정보 생성자
    @Builder
    public PersonalInf(String name, String birth, String phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
