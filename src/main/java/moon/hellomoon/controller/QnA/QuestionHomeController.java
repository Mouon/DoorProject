package moon.hellomoon.controller.QnA;


import moon.hellomoon.dto.QnA.QuestionResponse;
import moon.hellomoon.service.QnA.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionHomeController {

    QuestionService questionService;

    @GetMapping("question/list/top")
    public ResponseEntity<List<QuestionResponse>> ProjectStaticByTag(){
        List<QuestionResponse> questions = questionService.showTopQuestionList();
        return ResponseEntity.ok(questions);
    }


}
