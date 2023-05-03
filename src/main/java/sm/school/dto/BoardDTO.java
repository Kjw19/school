package sm.school.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    @NotEmpty(message = "제목을 작성해야합니다.")
    private String title;

    @NotEmpty(message = "내용을 작성해야합니다.")
    private String content;
    private Member member;
    private Date date;
    private Date modify_date;
    private String picture;

    @Builder
    public BoardDTO(Long id, String title, String content, Member member, Date date, Date modify_date, String picture) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.date = date;
        this.modify_date = modify_date;
        this.picture = picture;
    }

    //BoardDTO를 Board로 변환하는 메서드
    public Board toBoard() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .date(date)
                .modify_date(modify_date)
                .picture(picture)
                .build();
    }
}
