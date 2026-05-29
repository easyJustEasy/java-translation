package translation.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    @Autowired
    private LlamaChatService translator;

    public String translate(String text, String sourceLanguage, String targetLanguage) throws Exception {
        String promot = String.format("将以下文本从%s翻译成%s，只输出翻译结果：\\n\\n文本: %s", sourceLanguage, targetLanguage, text);
        return translator.chat(promot, null);
    }

    public String translateZh2En(String text) throws Exception {
        return translate(text, "中文", "英文");
    }
    public String translateEn2Zh(String text) throws Exception {
        return translate(text, "英文","中文");
    }

}
