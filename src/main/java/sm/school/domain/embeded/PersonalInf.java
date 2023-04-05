package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class PersonalInf {

    private String name;
    private int birth;
    private int phone;

    protected  PersonalInf() {}

    @Builder
    public PersonalInf(String name, int birth, int phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }
}
