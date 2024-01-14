package moon.hellomoon.controller.member;

import moon.hellomoon.domain.Member;
import moon.hellomoon.dto.member.MemberCreateRequest;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberManageController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    /**
     * 생성자를 통해서 MemberController에 memberService가 주입이 됩니다.
     * */
    @Autowired
    public MemberManageController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository=memberRepository;

    }
    @PostMapping("/members/new")
    public String create(MemberCreateRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        memberService.join(member);

        return "redirect:/";
    }

    @PostMapping("/member/delete")
    public ResponseEntity<?> delete(@RequestParam String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Member member = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            if(memberService.checkPassword(member.getId(),password)){
                memberService.deleteMember(member.getId());
                return ResponseEntity.ok().body("Member deleted successful");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
