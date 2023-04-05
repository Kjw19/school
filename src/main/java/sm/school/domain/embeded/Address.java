package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String zipcode;
    private String address;
    private String deAddress;

    protected Address() {}

    @Builder
    public Address(String zipcode, String address, String deAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.deAddress = deAddress;
    }
}


