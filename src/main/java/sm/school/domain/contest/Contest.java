package sm.school.domain.contest;

import lombok.Getter;
import sm.school.domain.member.Member;

import javax.persistence.*;

@Entity
@Getter
public class Contest {
    @Id
    @GeneratedValue
    @Column(name = "contest_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String con_name;//컨테스트 이름
    private String con_inf;//컨테스트 정보
    private String con_picture;//대문사진


}
