package sm.school.domain.club;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubRoom {

    @Id//기본값
    @GeneratedValue
    @Column(name = "clubRoom_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "club_id")//참여 동아리
    private List<Club> clubs=new ArrayList<>();
    @Builder
    public ClubRoom(Long id, List<Club> clubs) {
        this.id = id;
        this.clubs = clubs;
    }

    //참여 동아리 수정
    public void ModifyClub(List<Club> clubs){
        this.clubs=clubs;
    }
}
