package sm.school.domain.club;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import sm.school.domain.enumType.LocationType;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {
    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;//기본값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//클럽 협동 생성자

    @NotBlank
    @Column(unique = true)
    private String clubName;//클럽 협동 이름

    @NotEmpty
    private String clubInf;//협동 내용

    @NotEmpty
    private String clubPicture;//대문 사진

    @NotEmpty
    private String clubs;//협동동아리

    @Enumerated(EnumType.STRING)
    private LocationType locationType;//개최 주소

    @NotEmpty
    private String location;//개최 상세 장소

    @NotEmpty
    @CreatedDate
    @Column(name = "club_reg_date")
    private Date date;//생성 날짜

    @NotEmpty
    @ColumnDefault("1")
    @Column(name = "club_reg_type")
    private int regType;//1번 즉시가입 2번 승인 후 가입 3번 관리자
    @Builder
    public Club(Long id, Member member, String clubName, String clubInf, String clubPicture, String clubs, LocationType locationType, String location, Date date, int regType) {
        this.id = id;
        this.member = member;
        this.clubName = clubName;
        this.clubInf = clubInf;
        this.clubPicture = clubPicture;
        this.clubs = clubs;
        this.locationType = locationType;
        this.location = location;
        this.date = date;
        this.regType = regType;
    }



    public void ModifyClub(String clubName,String clubInf,String clubs,String clubPicture,int regType){
        this.clubName=clubName;
        this.clubInf = clubInf;
        this.clubs = clubs;
        this.clubPicture = clubPicture;
        this.regType = regType;

    }
}
