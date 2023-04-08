package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
public class PersonalInf {

    @NotEmpty
    private String name;
    @NotEmpty
    private int birth;
    @NotEmpty
    private int phone;

    protected  PersonalInf() {}

    @Builder
    public PersonalInf(String name, int birth, int phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
