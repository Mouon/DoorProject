package moon.hellomoon.controller.bookmark;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookmarksEditController {
    @GetMapping("/bookmark")
    public String bookmark(){
        return "bookmark/bookmark";
    }
}
