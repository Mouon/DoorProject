package moon.hellomoon.controller.diary;

import ch.qos.logback.core.model.Model;
import moon.hellomoon.dto.DiaryForm;
import moon.hellomoon.service.diary.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

    @GetMapping("/diary")
    public String showRestaurants(Model model) {
        return "service/diary";
    }
}
