package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
public class PersonalInf {

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String name;
    @NotEmpty
    private int birth;
    @NotEmpty
    private int phone;

    protected  PersonalInf() {}

    //개인정보 생성자
    @Builder
    public PersonalInf(String name, int birth, int phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
