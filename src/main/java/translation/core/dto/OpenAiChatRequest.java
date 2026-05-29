package translation.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OpenAiChatRequest {
    private String model;
    private List<Message> messages;
    private boolean stream;
    private double temperature;
    private int max_tokens;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }
}