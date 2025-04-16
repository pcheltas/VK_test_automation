package homework_3.tests;

import homework_3.annotations.BotRequired;
import homework_3.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Test class for login page functionality verification.
 * <p>
 * Contains tests for both successful and unsuccessful login scenarios.
 * </p>
 * <p>
 * Inherits common test setup from {@link BasicTest}.
 * </p>
 */
@Timeout(10)
@DisplayName("Login page test")
@Tag("smoke")
public class LoginTest extends BasicTest {

    /**
     * Verifies successful login with valid credentials.
     * <p>
     * Expected result: User profile link should display the bot's username.
     * </p>
     */
    @DisplayName("Successful login")
    @BotRequired
    @Test
    void login_givenValid_shouldSuccessfulLogin() {
        new LoginPage()
                .login(bot.login(), bot.password())
                .verifyUserProfileLinkContainsUsername(bot.username());
    }


    /**
     * Parameterized test for invalid login scenarios.
     * <p>
     * Verifies that the system properly handles invalid credentials
     * </p>
     *
     * @param email invalid email
     * @param password invalid password
     * @see #loginDataProvider()
     */
    @DisplayName("Unsuccessful login")
    @ParameterizedTest
    @MethodSource("loginDataProvider")
    void login_givenInvalid_shouldShowError(String email, String password) {
        new LoginPage()
                .loginExpectingError(email, password)
                .verifyErrorMessageVisible();
    }

    /**
     * Provides test data for invalid login scenarios
     *
     * @return {@code Stream<Arguments>} containing invalid email/password combinations
     */
    static Stream<Arguments> loginDataProvider() {
        return Stream.of(
                arguments("invalid@email.com", "wrongPassword"),
                arguments("oeifhwuivh@ewegf.com", "kuegvhsdjkm"),
                arguments("veryInvalid@email.com", "absolutelyWrongPassword")
        );
    }
}