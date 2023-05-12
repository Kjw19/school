package sm.school.Controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sm.school.Service.BoardService;
import sm.school.Service.MemberDetailsService;
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

        model.addAttribute("boards", boardService.findBoard());

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
        //사용자 정보를 받아 memberDetails에 저장
        boardDTO.setMember(((MemberDetailsService) authentication.getPrincipal()).getMember()); //코드 간소화

        boardService.createBoard(boardDTO);

        return "redirect:/board/";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam("id") long id, Model model) {


        model.addAttribute("board", boardService.selectBoard(id));

        return "board/boardDetail";
    }

    @GetMapping("/update")
    public String boardUpdateForm(@RequestParam long id, Model model) {

        BoardDTO boardDTO = boardService.selectBoard(id);
        model.addAttribute("boardDTO", boardDTO);

        return "board/boardUpdate";
    }
    @PostMapping("/update")
    public String boardUpdate(@ModelAttribute("boardDTO") BoardDTO boardDTO) {

        boardService.updateBoard(boardDTO);

        return "redirect:/board/";
    }

    @RequestMapping("/delete")
    public String boardDelete(@RequestParam("id") Long id) {

        Boolean deleteBoard = boardService.deleteBoard(id);

        if (deleteBoard){
            return "redirect:/board/";
        }else {
            return "redirect:/errorPage";
        }
    }
}
