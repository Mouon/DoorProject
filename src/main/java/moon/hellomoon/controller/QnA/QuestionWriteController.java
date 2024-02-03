package moon.hellomoon.controller.QnA;

import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.QnA.QuestionRequest;
import moon.hellomoon.dto.project.ProjectInsertRequest;
import moon.hellomoon.repository.jpaRepository.MemberRepository;
import moon.hellomoon.service.QnA.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionWriteController {

    QuestionService questionService;
    MemberRepository memberRepository;

    @Autowired
    public QuestionWriteController(QuestionService questionService, MemberRepository memberRepository) {
        this.questionService = questionService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            request.setMemberId(member.getId());
            questionService.addQuestion(request);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
