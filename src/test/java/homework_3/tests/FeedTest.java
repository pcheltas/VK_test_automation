package homework_3.tests;

import homework_3.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FeedTest extends BasicTest {

    @DisplayName("Verify feed is not empty")
    @Test
    void feed_shouldNotBeEmpty() {
        new LoginPage()
                .open()
                .login(bot.login(), bot.password())
                .feed()
                .shouldNotBeEmpty();
    }
}
