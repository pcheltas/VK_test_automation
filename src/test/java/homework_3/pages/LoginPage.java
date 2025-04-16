package homework_3.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Represents the login page of a social network, providing functionality for user authentication.
 * <p>
 * Contains all necessary elements and methods to:
 * <ul>
 *   <li>Perform successful login</li>
 *   <li>Handle failed login attempts</li>
 *   <li>Verify error messages</li>
 * </ul>
 * </p>
 */
public class LoginPage {
    private final SelenideElement loginField = $x("//*[@id='field_email']");
    private final SelenideElement passwordField = $x("//*[@id='field_password']");
    private final SelenideElement submitButton = $x("//input[@type='submit']");
    private final SelenideElement errorMessage = $x("//*[contains(@class, 'login_error')]");

    /**
     * Performs a successful login operation.
     *
     * @param email email or login to log in
     * @param password password to log in
     * @return new instance of {@code HomePage} after successful login
     */
    public HomePage login(String email, String password) {
        loginField.shouldBe(visible.because("Login field needed for login entry"))
                .setValue(email);
        passwordField.shouldBe(visible.because("Password field needed for password entry"))
                .setValue(password);
        submitButton.shouldBe(visible.because("Submit button needed to log in"))
                .click();
        return page(HomePage.class);
    }

    /**
     * Attempts to log in with invalid credentials, expecting to remain on the login page.
     *
     * @param email email or login to attempt
     * @param password password to attempt
     * @return current {@code LoginPage} instance
     */
    public LoginPage loginExpectingError(String email, String password) {
        loginField.shouldBe(visible.because("Login field needed for login entry"))
                .setValue(email);
        passwordField.shouldBe(visible.because("Password field needed for password entry"))
                .setValue(password);
        submitButton.shouldBe(visible.because("Submit button needed for logging attempt"))
                .click();
        return this;
    }

    /**
     * Verifies that the login error message is visible.
     *
     * @return current {@code LoginPage} instance
     */
    public LoginPage verifyErrorMessageVisible() {
        errorMessage.shouldBe(visible.because("Message should be visible to verify it"));
        return this;
    }
}
