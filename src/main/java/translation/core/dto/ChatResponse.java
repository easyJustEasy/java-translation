package translation.core.dto;

import lombok.Data;

@Data
public class ChatResponse {
    private String reply;            // llama-server 生成的回复
}