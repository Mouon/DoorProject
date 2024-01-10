package moon.hellomoon.service.ai;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AiAdviceService {

    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    public ResponseEntity<String> ask(Map<String, String> prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";



        // OpenAI 요청 본문 구성
        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new JSONObject[] {
                new JSONObject().put("role", "system").put("content", "Please provide advice in Korean based on the user's schedule. For example, if there is an event like 'Payday,' suggest something like 'Since you received your paycheck today, how about some shopping?' And for events like 'Dentist Appointment,' provide advice on preparations before the dental visit. 한문장만으로 말해주기"),
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
