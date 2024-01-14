package moon.hellomoon.controller.member;

import moon.hellomoon.domain.Member;
import moon.hellomoon.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberListController {
    private final MemberService memberService;
    public MemberListController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
