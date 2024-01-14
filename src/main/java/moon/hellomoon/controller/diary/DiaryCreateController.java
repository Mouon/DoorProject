package moon.hellomoon.controller.diary;

import lombok.extern.slf4j.Slf4j;
import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.diary.DiaryCreateRequest;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.diary.DiaryInsertService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DiaryCreateController {

    private final MemberRepository memberRepository;

    private final DiaryInsertService diaryInsertService;
    private final DiaryRepository diaryRepository;

    public DiaryCreateController(DiaryInsertService diaryInsertService, MemberRepository memberRepository, DiaryRepository diaryRepository){
        this.diaryInsertService = diaryInsertService;
        this.memberRepository=memberRepository;
        this.diaryRepository=diaryRepository;
    }

    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(@RequestBody DiaryCreateRequest diaryCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            diaryCreateRequest.setMemberId(member.getId());
        }
        System.out.println("Received eventDescription: " + diaryCreateRequest.getEventDescription());
        diaryInsertService.insertEvent(diaryCreateRequest);
        return ResponseEntity.ok().build();
    }
}
