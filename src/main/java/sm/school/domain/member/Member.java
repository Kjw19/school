package sm.school.domain.member;

import lombok.Getter;
import sm.school.domain.embeded.Address;
import sm.school.domain.embeded.PersonalInf;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String user_id;
    private int passwd;
    private String school;
    private String major;

    @Embedded
    private PersonalInf personalInf;

    @Embedded
    private Address address;



}
