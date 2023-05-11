package sm.school.domain.member;

import lombok.*;
import sm.school.dto.AddressDTO;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    @Column(nullable = false)
    private String zipcode;
    @Column(nullable = false)
    private String address1;
    @Column(nullable = false)
    private String address2;



    //address 생성자
    @Builder
    public Address(String zipcode, String address1, String address2) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public AddressDTO toAddressDTO() {
        return AddressDTO.builder()
                .zipcode(zipcode)
                .address1(address1)
                .address2(address2)
                .build();
    }

}


