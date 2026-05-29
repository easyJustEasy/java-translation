package translation.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import translation.core.service.TranslationService;

@RestController
@RequestMapping("/api")
@Slf4j
public class TranslationController {
    @Autowired
    private TranslationService translator;
    @PostMapping("/translate")
    public String translate(String text, String sourceLanguage, String targetLanguage) throws Exception {
        return translator.translate(text, sourceLanguage, targetLanguage);
    }
}
