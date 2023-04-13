package sm.school.domain.contest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;

import javax.persistence.*;
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
    private String con_name;//대회 이름
    @NotEmpty
    private String con_inf;//대회 정보
    @NotEmpty
    private String con_picture;//대문사진
    @NotEmpty
    @CreationTimestamp
    @Column(name = "contest_reg_date")
    private Date date;//대회 생성 날짜
    @NotEmpty
    @Column(name = "contest_role")
    @ColumnDefault("1")
    private int reg_type;//1번 즉시가입 2번 승인 후 가입

    //생성자

    @Builder
    public Contest(Long id, Member member, String con_name, String con_inf, String con_picture, Date date, int reg_type) {
        this.id = id;
        this.member = member;
        this.con_name = con_name;
        this.con_inf = con_inf;
        this.con_picture = con_picture;
        this.date = date;
        this.reg_type = reg_type;
    }
    public void ModifyContest(String con_name, String con_inf, String con_picture, int reg_type){
        this.con_name=con_name;
        this.con_inf=con_inf;
        this.con_picture=con_picture;
        this.reg_type=reg_type;
    }
}

