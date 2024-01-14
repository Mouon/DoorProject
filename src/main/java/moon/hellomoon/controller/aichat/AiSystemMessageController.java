package moon.hellomoon.controller.aichat;

import moon.hellomoon.service.ai.AiSystemMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * 성격 추가 되어도 서버 코드 수정 필요없게 구성
 * */
@RestController
public class AiSystemMessageController {

    @Autowired
    private AiSystemMessageService aiSystemMessageService;

    @GetMapping("/systemMessages")
    public ResponseEntity<Map<String, String>> getSystemMessages() {
        Map<String, String> systemMessages = aiSystemMessageService.getSystemMessageKeys();
        return ResponseEntity.ok(systemMessages);
    }
}
