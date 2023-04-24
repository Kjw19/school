package sm.school.domain.contest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import sm.school.domain.enumType.ContestType;
import sm.school.domain.enumType.LocationType;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contest {
    @Id
    @GeneratedValue
    @Column(name = "contest_id")
    private Long id;//기본값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//대회 생성한 사람

    @NotEmpty
    @Column(unique = true)
    private String conName;//대회 이름

    @NotEmpty
    private String conInf;//대회 정보


    @Enumerated(EnumType.STRING)
    private ContestType contestType;//대회 카테고리

    @Enumerated(EnumType.STRING)
    private LocationType locationType;//개최 주소

    @NotEmpty
    private String location;//개최 상세 장소


    private String conPicture;//대문사진


    @CreatedDate
    @Column(name = "contest_reg_date")
    private Date date;//대회 생성 날짜


    @Column(name = "contest_role")
    @ColumnDefault("1")
    private Integer regType;//1번 즉시가입 2번 승인 후 가입 (int->integer 변경 null값을 담을수 없어서)

    //생성자
    @Builder
    public Contest(Long id, Member member, String conName, String conInf, ContestType contestType,
                   LocationType locationType, String location, String conPicture, Date date, Integer regType) {
        this.id = id;
        this.member = member;
        this.conName = conName;
        this.conInf = conInf;
        this.contestType = contestType;
        this.locationType = locationType;
        this.location = location;
        this.conPicture = conPicture;
        this.date = date;
        this.regType = regType;
    }




    public void ModifyContest(String conName, String conInf, String conPicture, int regType){
        this.conName=conName;
        this.conInf=conInf;
        this.conPicture=conPicture;
        this.regType=regType;
    }
}

