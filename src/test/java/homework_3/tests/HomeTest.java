package homework_3.tests;

import homework_3.annotations.BotRequired;
import homework_3.pages.HomePage;
import homework_3.pages.LoginPage;
import org.junit.jupiter.api.*;

import java.util.stream.Stream;

/**
 * Test class for home page functionality.
 * <p>
 * Contains nested test classes for different home page components:
 * <ul>
 *   <li>{@link Feed} - tests for news feed functionality</li>
 *   <li>{@link Navigation} - tests for navigation menu behavior</li>
 * </ul>
 * </p>
 * <p>
 * Inherits common test setup from {@link BasicTest}.
 * </p>
 */
@Timeout(5)
@Tag("smoke")
@BotRequired
public class HomeTest extends BasicTest {

    /**
     * Nested test class for feed-related functionality verification.
     */
    @Nested
    @Timeout(10)
    @DisplayName("Feed test")
    @BotRequired
    class Feed {

        /**
         * Verifies that the news feed contains content after successful login.
         */
        @DisplayName("Verify feed is not empty")
        @Test
        void feed_shouldNotBeEmpty() {
            new LoginPage()
                    .login(bot.login(), bot.password())
                    .verifyFeedIsNotEmpty();
        }
    }

    /**
     * Nested test class for navigation menu verification.
     */
    @Nested
    @DisplayName("Navigation test")
    @BotRequired
    class Navigation {

        /**
         * Generates dynamic tests for verifying active state of navigation items.
         * <p>
         * Tests that navigation components become active when selected for:
         * <ul>
         *   <li>Main page</li>
         *   <li>Hobbies page</li>
         *   <li>Friends page</li>
         * </ul>
         * </p>
         *
         * @return {@code Stream} of dynamic tests
         */
        @DisplayName("Selected page is active in navigation bar")
        @TestFactory
        Stream<DynamicTest> nav_testNavActivePages() {
            HomePage page = new LoginPage().login(bot.login(), bot.password());
            return Stream.of(
                    "userMain",
                    "hobby",
                    "userFriend"
            ).map(featureName ->
                    DynamicTest.dynamicTest(
                            featureName + " in navigation should be active",
                            () -> testActivePage(featureName, page))
            );
        }

        /**
         * Helper method for verifying navigation item active state.
         *
         * @param featureName the navigation item identifier
         * @param page the HomePage instance to test against
         */
        void testActivePage(String featureName, HomePage page) {
            page.goToPageByName(featureName)
                    .verifyNavLinkByNameIsActive(featureName);
        }
    }
}
