package sm.school.domain.member;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@NoArgsConstructor
public class PersonalInf {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String phone;

    //개인정보 생성자
    @Builder
    public PersonalInf(String name, String birth, String phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
