package sm.school.domain.contest;

import lombok.*;
import sm.school.domain.enumType.ContestType;
import sm.school.domain.enumType.LocationType;
import sm.school.domain.member.Member;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContestDTO {

    @NotEmpty(message = "대회 이름은 필수")
    //private Member member;
    private String conName;
    private String conInf;
    private ContestType contestType;
    private LocationType locationType;
    private String location;
    private String conPicture;
    private Integer regType;
    @Builder
    public ContestDTO(/*Member member*/ String conName, String conInf, ContestType contestType
            , LocationType locationType, String location, String conPicture, Integer regType) {
        //this.member = member;
        this.conName = conName;
        this.conInf = conInf;
        this.contestType = contestType;
        this.locationType = locationType;
        this.location = location;
        this.conPicture = conPicture;
        this.regType = regType;
    }

    public Contest toContestEntity(){
        return Contest.builder()
                //.member(this.member)
                .conName(this.conName)
                .conInf(this.conInf)
                .contestType(this.contestType)
                .locationType(this.locationType)
                .location(this.location)
                .conPicture(this.conPicture)
                .regType(this.regType)
                .build();
    }
}
