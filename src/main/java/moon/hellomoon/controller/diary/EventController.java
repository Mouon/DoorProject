package moon.hellomoon.controller.diary;

import jdk.jfr.Event;
import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.domain.Diary;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.BoardForm;
import moon.hellomoon.dto.DiaryForm;
import moon.hellomoon.repository.DiaryRepository;
import moon.hellomoon.repository.MemberRepository;
import moon.hellomoon.service.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class EventController {

    private final MemberRepository memberRepository;

    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;

    public EventController(DiaryService diaryService,MemberRepository memberRepository,DiaryRepository diaryRepository){
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


    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(@RequestBody DiaryForm diaryForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            diaryForm.setMemberId(member.getId());
        }

        diaryService.insertEvent(diaryForm);
        return ResponseEntity.ok().build();
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
