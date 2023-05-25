package sm.school.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Address;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDto {

    @NotEmpty(message = "우편번호는 필수입니다.") //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String zipcode;
    @NotEmpty(message = "주소는 필수입니다.")
    private String address1;
    @NotEmpty(message = "상세 주소는 필수입니다.")
    private String address2;

    @Builder
    public AddressResponseDto(String zipcode, String address1, String address2) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }


    //Address 를 AddressDTO로 변환하는 메서드
    public static AddressResponseDto toDto(Address address) {
        return AddressResponseDto.builder()
                .zipcode(address.getZipcode())
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .build();
    }
}
