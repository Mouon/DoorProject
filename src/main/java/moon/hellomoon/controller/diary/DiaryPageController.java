package moon.hellomoon.controller.diary;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.service.diary.DiaryInsertService;
import moon.hellomoon.service.diary.DiaryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiaryPageController {
    @Autowired
    private DiaryListService diaryListService;

    @GetMapping("/editDiary/{eventId}")
    public String showEditDiaryPage(@PathVariable Long eventId, Model model) {
        Diary diary = diaryListService.findDiaryById(eventId);
        model.addAttribute("currentEventDescription", diary.getEventDescription());
        return "service/edit-diary";
    }

    @GetMapping("/diary")
    public String showRestaurants(Model model) {
        return "service/diary";
    }
}
