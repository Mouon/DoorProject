package moon.hellomoon.controller.board;

import moon.hellomoon.dto.Board.BoardWriteRequest;
import moon.hellomoon.service.board.BoardWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BoardWriteController {

    private final BoardWriteService boardWriteService;

    @Autowired
    public BoardWriteController(BoardWriteService boardWriteService){
        this.boardWriteService=boardWriteService;
    }

    @GetMapping("board/write")
    public String write(){
        return "community/write";
    }
    @PostMapping("/board-post")
    public String writeBoard(@ModelAttribute("boardWriteRequest") BoardWriteRequest boardWriteRequest) throws Exception{
        boardWriteService.insertBoard(boardWriteRequest);
        return "redirect:/board/board-list";
    }
}
