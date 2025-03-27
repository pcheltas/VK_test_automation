package homework_3.tests;

import homework_3.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;

@DisplayName("Login page test")
public class LoginTest extends BasicTest {

    @DisplayName("Successful login")
    @Test
    void login_givenValid_shouldSuccessfulLogin() {
        new LoginPage()
                .open()
                .login(bot.login(), bot.password())
                .navigation()
                .getUserProfileLink()
                .shouldHave(text(bot.username()));
    }

    @DisplayName("Unsuccessful login")
    @Test
    void login_givenInvalid_shouldShowError() {
        new LoginPage()
                .open()
                .loginExpectingError("invalid@email.com", "wrongPassword")
                .verifyErrorMessageVisible();
    }
}