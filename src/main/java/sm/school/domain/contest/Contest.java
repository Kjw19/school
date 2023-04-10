package sm.school.domain.contest;

import lombok.Getter;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Contest {
    @Id
    @GeneratedValue
    @Column(name = "contest_id")
    private Long id;//기본값
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//대회 생성한 사람

    @NotEmpty
    private String con_name;//대회 이름
    @NotEmpty
    private String con_inf;//대회 정보
    @NotEmpty
    private String con_picture;//대문사진


}

