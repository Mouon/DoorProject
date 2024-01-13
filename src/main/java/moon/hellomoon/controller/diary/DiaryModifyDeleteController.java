package moon.hellomoon.controller.diary;

import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.diary.DiaryModifyRequest;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.diary.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Slf4j
public class DiaryModifyDeleteController {

    private final MemberRepository memberRepository;

    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;

    public DiaryModifyDeleteController(DiaryService diaryService, MemberRepository memberRepository, DiaryRepository diaryRepository){
        this.diaryService=diaryService;
        this.memberRepository=memberRepository;
        this.diaryRepository=diaryRepository;
    }

    @PostMapping("/deleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
        }
        diaryService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    /**
     * @RequestBody String newEventDescription는 요청 본문을 단순 문자열로 처리해서 문제되지만
     * @RequestBody DiaryModifyRequest diaryModifyRequest는 요청 본문을 DiaryModifyRequest 타입의 객체로 자동 변환
     * JSON 요청이 `{"eventDescription": "새로운 설명"}형태로 올 경우, Spring은 이 JSON을 파싱하여 DiaryModifyRequest 객체의 eventDescription 필드에 새로운 설명` 값을 설정
     * */
    @PostMapping("/modifyEvent/{eventId}")
    public ResponseEntity<?> modifyEvent(@PathVariable Long eventId, @RequestBody DiaryModifyRequest diaryModifyRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
        }
        String newEventDescription = diaryModifyRequest.getEventDescription();
        diaryService.modifyEvent(eventId,newEventDescription);
        return ResponseEntity.ok().build();
    }


}
