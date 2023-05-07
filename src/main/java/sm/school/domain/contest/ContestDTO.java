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

    private Long id;
    private Member member;
    private String conName;
    private String conInf;
    private ContestType contestType;
    private LocationType locationType;
    private String location;
    private String conPicture;
    private Date date;
    private Integer regType;
    @Builder
    public ContestDTO(Long id,Member member, String conName, String conInf, ContestType contestType
            , LocationType locationType, String location, String conPicture,Date date, Integer regType) {
        this.id=id;
        this.member = member;
        this.conName = conName;
        this.conInf = conInf;
        this.contestType = contestType;
        this.locationType = locationType;
        this.location = location;
        this.conPicture = conPicture;
        this.date=date;
        this.regType = regType;
    }

    public Contest toContestEntity(){
        return Contest.builder()
                .id(this.id)
                .member(this.member)
                .conName(this.conName)
                .conInf(this.conInf)
                .contestType(this.contestType)
                .locationType(this.locationType)
                .location(this.location)
                .conPicture(this.conPicture)
                .date(this.date)
                .regType(this.regType)
                .build();
    }
}
