package homework_3.tests;

import homework_3.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;

/**
 * Test class for login page functionality verification.
 * <p>
 * Contains tests for both successful and unsuccessful login scenarios.
 * </p>
 * <p>
 * Inherits common test setup from {@link BasicTest}.
 * </p>
 */
@DisplayName("Login page test")
public class LoginTest extends BasicTest {

    /**
     * Verifies successful login with valid credentials.
     * <p>
     * Expected result: User profile link should display the bot's username.
     * </p>
     */
    @DisplayName("Successful login")
    @Test
    void login_givenValid_shouldSuccessfulLogin() {
        new LoginPage()
                .login(bot.login(), bot.password())
                .getUserProfileLink()
                .shouldHave(text(bot.username()));
    }

    /**
     * Verifies that the system properly handles invalid credentials
     * <p>
     * Expected result: Error message is shown.
     * </p>
     */
    @DisplayName("Unsuccessful login")
    @Test
    void login_givenInvalid_shouldShowError() {
        new LoginPage()
                .loginExpectingError("invalid@email.com", "wrongPassword")
                .verifyErrorMessageVisible();
    }
}