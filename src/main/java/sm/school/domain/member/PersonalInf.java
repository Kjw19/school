package sm.school.domain.member;

import lombok.*;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PersonalInf {

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String name;

    @NotEmpty
    private String birth;

    @NotEmpty
    private String phone;

    //개인정보 생성자
    @Builder
    public PersonalInf(String name, String birth, String phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
