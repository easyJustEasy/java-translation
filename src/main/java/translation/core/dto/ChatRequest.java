package translation.core.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;          // 用户输入的消息
    private String sessionId;        // 可选，用于关联多轮对话历史
}