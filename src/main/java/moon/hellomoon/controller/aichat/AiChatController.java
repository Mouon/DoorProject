package moon.hellomoon.controller.aichat;

import moon.hellomoon.service.AiChatService;
import moon.hellomoon.service.SystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class AiChatController {
    private final AiChatService aiChatService;
    private final SystemMessageService systemMessageService;

    @Autowired
    public AiChatController(AiChatService aiChatService, SystemMessageService systemMessageService) {
        this.aiChatService = aiChatService;
        this.systemMessageService = systemMessageService;
    }

    public ResponseEntity<Map<String, String>> getSystemMessages() {
        Map<String, String> systemMessages = systemMessageService.getSystemMessageKeys();
        return ResponseEntity.ok(systemMessages);
    }

    @PostMapping("/testAsk")
    public ResponseEntity<String> ask(@RequestBody Map<String, String> prompt) {
        return aiChatService.ask(prompt);
    }
}
