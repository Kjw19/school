package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
public class Address {

    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String address;
    @NotEmpty
    private String deAddress;

    protected Address() {}

    @Builder
    public Address(String zipcode, String address, String deAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.deAddress = deAddress;
    }
}


