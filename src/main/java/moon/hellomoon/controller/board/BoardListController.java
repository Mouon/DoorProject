package moon.hellomoon.controller.board;

import moon.hellomoon.repository.repositoryInterface.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BoardListController {

    /**컨트롤러에 jpa 레포지토리 주입 -> 데이터 갖고 오기 위해*/
    @Autowired
    private BoardRepository boardRepository;
    /**검색된 게시글 목록을 Model 객체에 posts 라는 이름으로 추가합니다. 이렇게 하면 뷰 템플릿에서 posts라는 이름으로 이 데이터에 접근
     * 아무튼 객체들 조회됨 이유는 .findAll() 메서드는 jpa에서 지원하는데 기본적으로 List 자료형임
     * */
    @GetMapping("/board/board-list")
    public String boardList(Model model){
        model.addAttribute("posts", boardRepository.findAll());
        return "community/board";
    }


}
