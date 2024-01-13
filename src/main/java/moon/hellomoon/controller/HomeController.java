package moon.hellomoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/aichat")
    public String aiChat(){
        return "service/aichat";
    }
    @GetMapping("/reset-password")
    public String resetPassword(){
        return "members/reset-password";
    }

    @GetMapping("/members/member-detail")
    public String memberDetail(){
        return "members/memberDetail";
    }

    @GetMapping("/draw-code")
    public String drawCode(){
        return "service/drawCode";
    }


}
