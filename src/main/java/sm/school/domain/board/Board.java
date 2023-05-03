package sm.school.domain.board;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sm.school.domain.member.Member;
import sm.school.dto.BoardDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //Getter 및 생성자로만 접근 가능하도록 설정
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id; //기본 값

    @NotEmpty //해당 필드의 값이 null이 아니고, 비어있지 않아야 함
    private String title; //제목

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String content; //내용

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 방식
    @JoinColumn(name = "mem_id")
    private Member member; //게시글 작성자

    @Column(name = "board_date")
    @CreationTimestamp//insert일 경우 현재시간으로 시간 입력
    private Date date; //작성시간

    @Column(name = "board_modify_date")
    @UpdateTimestamp//update일 경우 현재시간으로 시간 입력
    private Date modify_date; //수정시간

    @Column(name = "board_pic")
    private String picture; //게시글 사진

    //게시글 생성자
    @Builder
    public Board(Long id, String title, String content,
                 Member member, Date date, Date modify_date,
                 String picture) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.date = date;
        this.modify_date = modify_date;
        this.picture = picture;
    }

    //게시글 수정 메서드
    public void ModifyBoard(String title, String content,
                            Date modify_date, String picture) {
        this.title = title;
        this.content = content;
        this.modify_date = modify_date;
        this.picture = picture;
    }

    public BoardDTO toBoardDTO() {
        return BoardDTO.builder()
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

