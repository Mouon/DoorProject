package moon.hellomoon.controller.aichat;

import moon.hellomoon.service.ai.AiChatService;
import moon.hellomoon.service.ai.AiSystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class AiChatController {
    private final AiChatService aiChatService;
    private final AiSystemMessageService aiSystemMessageService;

    @Autowired
    public AiChatController(AiChatService aiChatService, AiSystemMessageService aiSystemMessageService) {
        this.aiChatService = aiChatService;
        this.aiSystemMessageService = aiSystemMessageService;
    }

    public ResponseEntity<Map<String, String>> getSystemMessages() {
        Map<String, String> systemMessages = aiSystemMessageService.getSystemMessageKeys();
        return ResponseEntity.ok(systemMessages);
    }

    @PostMapping("/testAsk")
    public ResponseEntity<String> ask(@RequestBody Map<String, String> prompt) {
        return aiChatService.ask(prompt);
    }
}
