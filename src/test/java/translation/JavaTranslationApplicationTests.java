package translation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import translation.core.service.LlamaChatService;
import translation.core.service.TranslationService;

@SpringBootTest
class JavaTranslationApplicationTests {
    @Autowired
    private TranslationService translator;

    @Test
    void contextLoads() throws Exception {
        String rrr = translator.translateEn2Zh("The great rejuvenation of the Chinese nation");
        System.out.println(rrr);
    }
}
