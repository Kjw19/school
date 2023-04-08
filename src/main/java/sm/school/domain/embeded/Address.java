package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
public class Address {

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String zipcode;
    @NotEmpty
    private String address;
    @NotEmpty
    private String deAddress;

    protected Address() {}

    //address 생성자
    @Builder
    public Address(String zipcode, String address, String deAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.deAddress = deAddress;
    }
}


