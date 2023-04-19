package sm.school.domain.member;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String zipcode;
    @NotEmpty
    private String address1;
    @NotEmpty
    private String address2;

    //address 생성자
    @Builder
    public Address(String zipcode, String address1, String address2) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }

}


