package sm.school.domain.meeting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sm.school.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id//기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_id")
    private Long id;

    @Column(nullable = false)
    private String school; //학교

    @Column(nullable = false)
    private String major; // 전공

    @Column(nullable = false)
    private String region; //지역

    @Column(nullable = false)
    private int count; //미팅인원

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 방식
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;//미팅 생성 회원


    //미팅 등록
    @Builder
    public Meeting(Long id, String school, String major, String region, int count, Member member) {
        this.id = id;
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
        this.member = member;
    }

    //미팅 정보 수정
    public void ModifyMeeting(String school, String major, String region, int count) {
        this.school = school;
        this.major = major;
        this.region = region;
        this.count = count;
    }
}
