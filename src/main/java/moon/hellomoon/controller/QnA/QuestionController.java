package moon.hellomoon.controller.QnA;

import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.dto.QnA.QuestionResponse;
import moon.hellomoon.repository.jpaRepository.MemberRepository;
import moon.hellomoon.service.QnA.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QuestionController {

    QuestionService questionService;
    MemberRepository memberRepository;

    @Autowired
    public QuestionController(QuestionService questionService, MemberRepository memberRepository) {
        this.questionService = questionService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/questions-list")
    public String viewQuestions(Model model) {
        List<QuestionResponse> questions = questionService.showQuestionList();
        model.addAttribute("questions", questions);
        return "community/questionList";
    }

    @GetMapping("/question-write")
    public String questionWrite() {
        return "community/questionWrite";
    }

    @GetMapping("/questions-list/user")
    public String viewUserQuestions(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            List<QuestionResponse> questions = questionService.showUserQuestionList(member.getId());
            model.addAttribute("questions", questions);

            return "community/questionUserList";
        } else {
            return ResponseEntity.ok().toString();
        }
    }

}
