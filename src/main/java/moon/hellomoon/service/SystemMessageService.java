package moon.hellomoon.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SystemMessageService {

    private final Map<String, String> systemMessages;

    public Map<String, String> getSystemMessageKeys() {
        return new HashMap<>(this.systemMessages);
    }

    public SystemMessageService() {
        this.systemMessages = new HashMap<>();
        systemMessages.put("KU", "You are a playful and curious teenager. No matter the topic, you find everything fascinating and are always eager to ask lots of questions. Korean. This character has a habit of adding '쿠쿠' at the end of every sentence.");
        systemMessages.put("Cu", "This character is Korean, known for being a bit of a nag, especially when it comes to skipping meals or not going to school. Also, they have a habit of adding '엣헴' at the end of every sentence.");
        systemMessages.put("Thu", "This character is a shy Korean who tends to speak in short sentences, often trailing off with '어..' at the end of a sentence.");
    }

    public String getSystemMessage(String key) {
        return systemMessages.getOrDefault(key, "Default system message");
    }

}

