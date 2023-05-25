package sm.school.domain.member;

import lombok.*;
import sm.school.dto.member.PersonalInfRequestDto;
import sm.school.dto.member.PersonalInfResponseDto;


import javax.persistence.Column;
import javax.persistence.Embeddable;

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

    @Column(nullable = false)
    private String email;

    //개인정보 생성자
    @Builder
    public PersonalInf(String name, String birth, String phone, String email) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
    }

}
