package homework_3.tests;

import com.codeborne.selenide.Configuration;
import homework_3.model.Bot;
import homework_3.model.BotType;
import homework_3.services.BotFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;


public abstract class BasicTest {
    protected static Bot bot;

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://ok.ru";
        Configuration.timeout = 10000;
        bot = BotFactory.createBot(BotType.COMMON);
    }

    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }


}
