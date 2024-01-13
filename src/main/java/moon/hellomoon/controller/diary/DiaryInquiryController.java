package moon.hellomoon.controller.diary;

import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.diary.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DiaryInquiryController {
    private final MemberRepository memberRepository;

    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;

    public DiaryInquiryController(DiaryService diaryService,MemberRepository memberRepository,DiaryRepository diaryRepository){
        this.diaryService=diaryService;
        this.memberRepository=memberRepository;
        this.diaryRepository=diaryRepository;
    }

    /**
     * 위에 세 줄은 그냥 인증 받아오는가라고 생각하고 외워서 쓰자!
     * */
    @GetMapping("/getEvents")
    public ResponseEntity<List<Diary>> getEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            LocalDate currentDate = LocalDate.now();
            List<Diary> events = diaryRepository.findByMemberId(member.getId(),currentDate);
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * LocalDate.now()는 서버가 구동되고 있는 시스템의 현재 날짜를 가져오는 메소드
     * */
    @GetMapping("/getTodayEvents")
    public ResponseEntity<List<Diary>> getTodayEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            LocalDate currentDate = LocalDate.now();
            List<Diary> events = diaryRepository.findByMemberIdAndDate(member.getId(),currentDate);
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/getDailyEvents")
    public ResponseEntity<List<Diary>> getDailyEvents(@RequestParam("eventDate") String eventDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            LocalDate selectedDate = LocalDate.parse(eventDate);

            List<Diary> events = diaryRepository.findByMemberIdAndDate(member.getId(),selectedDate);
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
