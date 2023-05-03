package sm.school.Controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sm.school.Service.BoardService;
import sm.school.Service.MemberDetailsService;
import sm.school.domain.board.Board;
import sm.school.domain.member.Member;
import sm.school.dto.BoardDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String BoardList(Model model) {
        List<Board> boardList = boardService.findBoard();

        model.addAttribute("boards", boardList);

        return "board/list";
    }

    @GetMapping("/create")
    public String CreateBoardForm(@ModelAttribute("boardDTO") BoardDTO boardDTO, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }

        return "board/createBoard";
    }

    @PostMapping("/create")
    public String CreateBoard(@Valid BoardDTO boardDTO, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/member/login";
        }

        //사용자 정보를 받아 memberDetails에 저장
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        Member member = memberDetails.getMember();
        boardDTO.setMember(member);

        boardService.createBoard(boardDTO);

        return "redirect:/board/";
    }
}
