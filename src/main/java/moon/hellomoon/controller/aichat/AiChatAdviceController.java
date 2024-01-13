package moon.hellomoon.controller.aichat;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.ai.AiAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AiChatAdviceController {

    private final AiAdviceService aiAdviceService;
    private final MemberRepository memberRepository;

    private DiaryRepository diaryRepository;

    @Autowired
    public AiChatAdviceController(AiAdviceService aiAdviceService, MemberRepository memberRepository, DiaryRepository diaryRepository) {
        this.aiAdviceService = aiAdviceService;
        this.memberRepository = memberRepository;
        this.diaryRepository = diaryRepository;
    }

    @PostMapping("/aiAdvice")
    public ResponseEntity<String> advice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            Map<String, String> prompt = new HashMap<>();
            LocalDate currentDate = LocalDate.now();
            List<Diary> events = diaryRepository.findByMemberIdAndDate(member.getId(), currentDate);
            if (!events.isEmpty()) {
                Diary firstEvent = events.get(0);
                prompt.put("prompt", firstEvent.getEventDescription());
                return aiAdviceService.ask(prompt);
            } else {
                return ResponseEntity.ok("오늘 일정이 없습니다.");
            }

        }else {
            // 인증되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
