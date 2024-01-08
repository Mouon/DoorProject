package moon.hellomoon.controller;

import moon.hellomoon.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    private final MemberService memberService;

    @Autowired
    public PasswordController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Model model) {
        boolean passwordChanged = memberService.changePassword(email, oldPassword, newPassword);
        if (passwordChanged) {
            return "redirect:/";
        } else {
            model.addAttribute("passwordChangeError", "기존 비밀번호가 잘못되었습니다.");
            return "redirect:/reset-password";
        }
    }
}
