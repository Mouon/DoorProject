package moon.hellomoon.controller.aichat;

import moon.hellomoon.service.ai.AiDrawCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AiDrawCodeController {
    @Autowired
    private AiDrawCodeService aiDrawCodeService;

    @PostMapping("/aichat/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            String htmlCode = aiDrawCodeService.processImage(imageFile);
            return ResponseEntity.ok(htmlCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing image: " + e.getMessage());
        }
    }


}
