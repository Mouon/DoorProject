package moon.hellomoon.controller.diary;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.dto.DiaryForm;
import moon.hellomoon.service.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @GetMapping("/editDiary/{eventId}")
    public String showEditDiaryPage(@PathVariable Long eventId, Model model) {
        Diary diary = diaryService.findDiaryById(eventId);
        model.addAttribute("currentEventDescription", diary.getEventDescription());
        return "service/edit-diary";
    }

    @GetMapping("/diary")
    public String showRestaurants(Model model) {
        return "service/diary";
    }
}
