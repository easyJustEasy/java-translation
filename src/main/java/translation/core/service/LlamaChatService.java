package translation.core.service;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import translation.core.dto.OpenAiChatRequest;
import translation.core.dto.OpenAiChatResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LlamaChatService {

    @Value("${llama.server-url}")
    private String llamaServerUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * 调用 llama-server 的 /v1/chat/completions 端点
     * @param userMessage  用户当前输入
     * @param history      可选的历史对话（简单实现：仅拼接 role=user/assistant 消息）
     */
    public String chat(String userMessage, List<OpenAiChatRequest.Message> history) throws Exception {
        // 构造消息列表：先加历史，最后加当前用户消息
        if (history == null) {
            history = new ArrayList<>();
        }
        OpenAiChatRequest.Message currentUser = OpenAiChatRequest.Message.builder()
                .role("user")
                .content(userMessage)
                .build();
        history.add(currentUser);

        OpenAiChatRequest request = OpenAiChatRequest.builder()
                .model("local-model")  // 随便填，llama-server 会忽略
                .messages(history)
                .stream(false)
                .temperature(0.7)
                .max_tokens(512)
                .build();

        String requestJson = JSONObject.toJSONString(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(llamaServerUrl + "/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            log.error("llama-server 返回错误: {}", response.body());
            throw new RuntimeException("调用 llama-server 失败");
        }

        OpenAiChatResponse chatResponse = JSONObject.parseObject(response.body(), OpenAiChatResponse.class);
        if (chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            return "";
        }
        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}