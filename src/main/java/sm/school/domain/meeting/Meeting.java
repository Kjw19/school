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
    @GeneratedValue
    @Column(name = "meet_id")
    private Long id;

    @NotEmpty//해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String school; //학교

    @NotEmpty
    private String major; // 전공

    @NotEmpty
    private String region; //지역

    @NotEmpty
    private int mem_count; //미팅인원

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 방식
    @JoinColumn(name = "mem_id")
    private Member member;//미팅 생성 회원


    //미팅 등록
    @Builder
    public Meeting(Long id, String school, String major, String region, int mem_count, Member member) {
        this.id = id;
        this.school = school;
        this.major = major;
        this.region = region;
        this.mem_count = mem_count;
        this.member = member;
    }

    //미팅 정보 수정
    public void ModifyMeeting(String school, String major, String region, int mem_count) {
        this.school = school;
        this.major = major;
        this.region = region;
        this.mem_count = mem_count;
    }
}
