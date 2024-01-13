package moon.hellomoon.service.ai;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AiChatService {
    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    @Autowired
    private SystemMessageService systemMessageService;

    public ResponseEntity<String> ask(Map<String, String> prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        /**
         * JSON.stringify({ personality: personality, prompt: message })로 크라이언트가 메세지 보내면 personality로 value값 뽑아서
         * systemMessage에 넣고 new JSONObject().put("role", "system").put("content", systemMessage)이걸로 성격 주입
         * */
        String systemMessage = systemMessageService.getSystemMessage(prompt.get("personality"));

        // OpenAI 요청 본문 구성
        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new JSONObject[] {
                new JSONObject().put("role", "system").put("content", systemMessage),
                new JSONObject().put("role", "user").put("content", prompt.get("prompt"))
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            System.out.println("API Response: " + response.getBody());
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
