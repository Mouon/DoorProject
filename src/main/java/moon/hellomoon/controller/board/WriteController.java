package moon.hellomoon.controller.board;

import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.service.board.BoardWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WriteController {

    private final BoardWriteService boardWriteService;

    @Autowired
    public WriteController(BoardWriteService boardWriteService){
        this.boardWriteService=boardWriteService;
    }

    @GetMapping("board/write")
    public String write(){
        return "community/write";
    }
    @PostMapping("/board-post")
    public String writeBoard(@ModelAttribute("boardForm") BoardForm boardForm) throws Exception{
        boardWriteService.insertBoard(boardForm);
        return "redirect:/board/board-list";
    }
}
