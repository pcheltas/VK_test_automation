package homework_3.tests;

import com.codeborne.selenide.Configuration;
import homework_3.model.Bot;
import homework_3.model.BotType;
import homework_3.services.BotFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.*;


/**
 * Abstract base class for all UI tests in the application.
 * <p>
 * Provides common test infrastructure including:
 * <ul>
 *   <li>Basic Selenide configuration</li>
 *   <li>Bot instance creation</li>
 *   <li>Browser setup and cleanup</li>
 * </ul>
 * </p>
 * <p>
 * All concrete test classes should extend this class to inherit the common test setup.
 * </p>
 */
public abstract class BasicTest {
    protected static Bot bot;

    /**
     * Sets up the test environment before all test methods.
     * <p>
     * Configures:
     * <ul>
     *   <li>Selenide base URL and timeout</li>
     *   <li>Creates a COMMON type bot instance</li>
     *   <li>Opens the base application URL</li>
     * </ul>
     * </p>
     */
    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://ok.ru";
        Configuration.timeout = 10000;
        bot = BotFactory.createBot(BotType.COMMON);
        open("/");
    }
    /**
     * Cleans up the test environment after each test method.
     * <p>
     * Performs:
     * <ul>
     *   <li>Browser cookies cleanup</li>
     *   <li>Local storage cleanup</li>
     * </ul>
     * </p>
     * <p>
     * Ensures test isolation between test methods.
     * </p>
     */
    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }


}