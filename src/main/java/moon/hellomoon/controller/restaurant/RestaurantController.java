package moon.hellomoon.controller.restaurant;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestaurantController {
    @GetMapping("/restaurant")
    public String showRestaurants(Model model) {
        return "service/restaurant";
    }
}
