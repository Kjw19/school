package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.BoardService;
import sm.school.dto.BoardDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String BoardList(Model model) {

        model.addAttribute("boards", boardService.findBoard());

        return "board/list";
    }

    @GetMapping("/create")
    public String CreateBoardForm(@ModelAttribute("boardDTO") BoardDTO boardDTO) {
        return "board/createBoard";
    }

    @PostMapping("/create")
    public String CreateBoard(@Valid BoardDTO boardDTO, @RequestParam("image") MultipartFile multipartFile,
                              Authentication authentication) {

        boardService.createBoard(boardDTO, multipartFile,authentication);

        return "redirect:/board/";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam("id") long id, Model model) {

        model.addAttribute("board", boardService.selectBoard(id));

        return "board/boardDetail";
    }

    @GetMapping("/update")
    public String boardUpdateForm(@RequestParam long id, Model model) {

        model.addAttribute("boardDTO", boardService.selectBoard(id));

        return "board/boardUpdate";
    }
    @PostMapping("/update")
    public String boardUpdate(@ModelAttribute("boardDTO") BoardDTO boardDTO) {

        boardService.updateBoard(boardDTO);

        return "redirect:/board/";
    }

    @RequestMapping("/delete")
    public String boardDelete(@RequestParam("id") Long id) {

       boardService.deleteBoard(id);
        return "redirect:/board/list";
    }
}
