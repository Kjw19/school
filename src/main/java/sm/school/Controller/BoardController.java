package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.BoardService;
import sm.school.dto.BoardDTO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
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

        boardService.createBoard(boardDTO, multipartFile, authentication);

        return "redirect:/board/list";
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
    public String boardUpdate(@ModelAttribute("boardDTO") BoardDTO boardDTO,
                              @RequestParam("image") MultipartFile multipartFile) {

        boardService.updateBoard(boardDTO, multipartFile);

        return "redirect:/board/detail?id=" + boardDTO.getId();
    }

    @RequestMapping("/delete")
    public String boardDelete(@RequestParam("id") Long id) {

        boardService.deleteBoard(id);
        return "redirect:/board/list";
    }

    @GetMapping("currentImage")
    public ResponseEntity<Map<String, String>> currentProfile(@RequestParam("id") Long id, Authentication authentication) {
        String currentImage = boardService.currentImage(id);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", currentImage);

        return ResponseEntity.ok(response);
    }
}
