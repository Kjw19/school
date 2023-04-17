package sm.school.domain.club;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
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
    private String club_name;//클럽 협동 이름

    @NotEmpty
    private String club_inf;//협동 내용

    @NotEmpty
    private String club_picture;//대문 사진

    @NotEmpty
    private String clubs;//협동동아리

    @NotEmpty
    private String location;//개최장소

    @NotEmpty
    @CreatedDate
    @Column(name = "club_reg_date")
    private Date date;//생성 날짜

    @NotEmpty
    @ColumnDefault("2")
    @Column(name = "club_reg_type")
    private int reg_type;//1번 즉시가입 2번 승인 후 가입
    @Builder
    public Club(Long id, Member member, String club_name, String club_inf
                ,String club_picture, String clubs, String location, Date date, int reg_type) {
        this.id = id;
        this.member = member;
        this.club_name = club_name;
        this.club_inf = club_inf;
        this.club_picture = club_picture;
        this.clubs = clubs;
        this.location = location;
        this.date = date;
        this.reg_type = reg_type;
    }
    public void ModifyClub(String club_name,String club_inf,String clubs,String club_picture,int reg_type){
        this.club_name=club_name;
        this.club_inf = club_inf;
        this.clubs = clubs;
        this.club_picture = club_picture;
        this.reg_type = reg_type;

    }
}
