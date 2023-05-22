package sm.school.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sm.school.Service.BoardReplyService;
import sm.school.Service.BoardService;
import sm.school.domain.reply.BoardReply;
import sm.school.dto.BoardDTO;
import sm.school.dto.reply.BoardReplyDTO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardReplyService replyService;

    @GetMapping("/list")
    public String BoardList(Model model) {

        model.addAttribute("boards", boardService.findBoard());

        return "board/list";
    }

    @GetMapping("/create")
    public String createBoardForm(@ModelAttribute("boardDTO") BoardDTO boardDTO) {
        return "board/createBoard";
    }

    @PostMapping("/create")
    public String createBoard(@Valid BoardDTO boardDTO, @RequestParam("image") MultipartFile multipartFile,
                              Authentication authentication) {

        boardService.createBoard(boardDTO, multipartFile, authentication);

        return "redirect:/board/list";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam("id") long id, Model model) {
        //댓글 불러오기
        List<BoardReplyDTO> replyDTOList = replyService.findReplyByBoardId(id);

        model.addAttribute("board", boardService.selectBoard(id));
        model.addAttribute("boardReplyDTO", new BoardReplyDTO());
        model.addAttribute("list", replyService.findReplyByBoardId(id));

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


    //게시글 댓글
    @GetMapping("/createReply")
    public String createReplyForm(@ModelAttribute("boardReplyDTO") BoardReplyDTO boardReplyDTO) {

        return "board/createReply";
    }

    @PostMapping("/createReply/{boardId}")
    public String createReply(@RequestParam("boardId") Long boardId, @Valid BoardReplyDTO boardReplyDTO,
                              @RequestParam("image") MultipartFile multipartFile, Authentication authentication) {
        replyService.createReply(boardReplyDTO, boardId, multipartFile, authentication);

        return "redirect:/board/detail?id=" + boardId;
    }

    @RequestMapping("/deleteReply")
    public String deleteReply(@RequestParam("replyId") Long replyId,
                              @RequestParam("boardId") Long boardId) {

        replyService.deleteReply(replyId);
        return "redirect:/board/detail?id=" + boardId;
    }

}
