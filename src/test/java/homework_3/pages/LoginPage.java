package homework_3.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement loginField = $("#field_email");
    private final SelenideElement passwordField = $("#field_password");
    private final SelenideElement submitButton = $("input[type='submit']");
    private final SelenideElement errorMessage = $(".input-e.login_error");

    public HomePage login(String email, String password) {
        loginField.setValue(email);
        passwordField.setValue(password);
        submitButton.click();
        return page(HomePage.class);
    }

    public LoginPage loginExpectingError(String email, String password) {
        loginField.setValue(email);
        passwordField.setValue(password);
        submitButton.click();
        return this;
    }

    public LoginPage verifyErrorMessageVisible() {
        errorMessage.shouldBe(visible);
        return this;
    }

    public LoginPage open() {
        Selenide.open("/");
        return this;
    }


}
