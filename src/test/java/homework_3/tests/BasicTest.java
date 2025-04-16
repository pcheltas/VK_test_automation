package homework_3.tests;

import com.codeborne.selenide.Configuration;
import homework_3.annotations.BotRequired;
import homework_3.model.Bot;
import homework_3.model.BotType;
import homework_3.services.BotFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.commons.support.AnnotationSupport;

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
        open("/");
    }

    /**
     * Initializes the bot instance conditionally based on test annotations.
     * <p>
     * This method performs a dynamic check for {@link BotRequired} annotation at both method and class levels.
     * If either the test method or its declaring class is annotated with {@code @BotRequired}, a new bot instance
     * will be created using {@link BotFactory}. The initialization occurs before each test execution.
     * </p>
     * @param testInfo JUnit's test metadata container (autowired by framework)
     */
    @BeforeEach
    void initBotIfNeeded(TestInfo testInfo) {
        boolean needsBot = testInfo.getTestMethod()
                .map(method -> AnnotationSupport.isAnnotated(method, BotRequired.class))
                .orElse(false)
                ||
                testInfo.getTestClass()
                        .map(clazz -> AnnotationSupport.isAnnotated(clazz, BotRequired.class))
                        .orElse(false);

        if (needsBot) bot = BotFactory.createBot(BotType.COMMON);
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
