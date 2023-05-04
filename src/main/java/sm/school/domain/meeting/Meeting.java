package sm.school.domain.meeting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id//기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String introduction; //소개 글

    @Column(nullable = false)
    private String school; //학교

    @Column(nullable = false)
    private String major; // 전공

    @Column(nullable = false)
    private String region; //지역

    @Column(nullable = false)
    private int count; //미팅인원

    @Column(name = "meetReg")
    @CreationTimestamp
    private Date date; //가입일자

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 방식
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;//미팅 생성 회원


    //미팅 등록
    @Builder
    public Meeting(Long id,String title, String introduction, String school, String major, String region, int count, Date date, Member member) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
        this.date = date;
        this.member = member;
    }

}
