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

    /**
     * prompt 는 맵으로 선언하고
     * prompt.put("prompt", firstEvent.getEventDescription()); 이걸로 prompt 키 값의
     * value 로 events 의 첫번째 일정 설명 저장
     * aiAdviceService.ask(prompt)로 질문 전달
     * ask 가 파라매터를 맵으로 받음
     * 그건 gpt api 에서 정해준거
     * */
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
