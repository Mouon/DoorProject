package moon.hellomoon.controller.projects;

import moon.hellomoon.dto.Board.BoardDetailResponse;
import moon.hellomoon.dto.project.ProjectDetailResponse;
import moon.hellomoon.service.project.ProjectDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectPageController {
    @Autowired
    private ProjectDetailService projectDetailService;
    @GetMapping("/write-project")
    public String writeProject(){
        return "community/projectWrite";
    }

    @GetMapping("/project-detail/{id}")
    public String getDetail(@PathVariable Long id, Model model) throws Exception {
        ProjectDetailResponse projectDetailResponse = projectDetailService.detail(id);
        model.addAttribute("projectDetailResponse", projectDetailResponse);//잘 감이 안오지만 odel 객체를 사용하여 뷰로 데이터를 전달합니다."boardForm" 이라는 이름으로 boardForm 객체를 모델에 추가

        return "community/projectDetail";
    }

}
