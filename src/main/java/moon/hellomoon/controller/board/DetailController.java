package moon.hellomoon.controller.board;

import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.service.board.BoardDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailController {

    @Autowired
    private BoardDetailService boardDetailService;

    /** @PathVariable을 통해서 "/detail/{id}" 에서 id 가져와서 "Long id"에 할당*/
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable Long id, Model model) throws Exception {
        BoardForm boardForm = boardDetailService.detail(id);//아이디를 기반으로 찾은 boardform을 BoardForm boardForm으로 생성
        model.addAttribute("boardForm", boardForm);//잘 감이 안오지만 odel 객체를 사용하여 뷰로 데이터를 전달합니다."boardForm" 이라는 이름으로 boardForm 객체를 모델에 추가

        return "community/detail";
    }

}
