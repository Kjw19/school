package sm.school.domain.club;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import sm.school.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember {
    @Id
    @GeneratedValue
    @Column(name = "club_mem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;//협동 동아리 참가한 사람

    @Column(name = "club_date")
    @CreatedDate
    private Date date;//참여 날짜

    @Column(name = "club_mem_type")
    @ColumnDefault("1")//1.기본회원 2.차단회원
    private int reg_type;//권한

}
