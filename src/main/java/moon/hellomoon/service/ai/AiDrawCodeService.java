package moon.hellomoon.service.ai;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class AiDrawCodeService {

    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    public String processImage(MultipartFile imageFile) throws IOException {
        String imageDescription = requestImageDescriptionFromOpenAI(imageFile);
        return requestHtmlCodeFromOpenAI(imageDescription);
    }

    private String requestImageDescriptionFromOpenAI(MultipartFile imageFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Path tempFile = Files.createTempFile(null, null);
        imageFile.transferTo(tempFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", new JSONArray()
                .put(new JSONObject().put("type", "text").put("text", "이 코드 디자인에 대해 설명해줘."))
                .put(new JSONObject().put("type", "image_url").put("image_url", tempFile.toUri().toString())));

        JSONObject body = new JSONObject();
        body.put("model", "gpt-4-vision-preview");
        body.put("messages", new JSONArray().put(message));
        body.put("max_tokens", 1000);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            System.out.println("API Response: " + response.getBody());
            // API 응답으로부터 이미지 설명 추출
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray messages = jsonResponse.getJSONArray("messages");
            for (int i = 0; i < messages.length(); i++) {
                JSONObject msg = messages.getJSONObject(i);
                if (msg.getString("role").equals("assistant")) {
                    return msg.getString("content");
                }
            }
            throw new RuntimeException("no description");
        } catch (Exception e) {
            System.err.println("Error sending request to OpenAI for image description: " + e.getMessage());
            throw new RuntimeException("Failed to get image description", e);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private String requestHtmlCodeFromOpenAI(String imageDescription) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        JSONObject body = new JSONObject();
        body.put("prompt", "Convert this image description to HTML: " + imageDescription);
        body.put("max_tokens", 150);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            System.out.println("API Response: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error sending request to OpenAI: " + e.getMessage());
            throw new RuntimeException("Failed to process image", e);
        }
    }
}


