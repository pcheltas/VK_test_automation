package homework_3.tests;

import homework_3.model.Bot;
import homework_3.model.BotType;
import homework_3.pages.FriendsPage;
import homework_3.pages.LoginPage;
import homework_3.services.BotFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * Test class for verifying friend-related functionality.
 * <p>
 * Inherits common test setup from {@link BasicTest}.
 * </p>
 */
@DisplayName("Friend page test")
public class FriendTest extends BasicTest {


    /**
     * Tests the complete friend addition workflow.
     *
     * @throws InterruptedException if any thread is interrupted during sleep/wait
     * @see BotFactory#createBot(BotType)
     * @see LoginPage
     * @see FriendsPage
     */
    @DisplayName("Successful friend adding")
    @Test
    void user_shouldAddFriend() throws InterruptedException {
        final Bot friend = BotFactory.createBot(BotType.FRIEND);
        new LoginPage()
                .login(friend.login(), friend.password())
                .goToFriendsPage()
                .searchFriendByName(bot.username())
                .sendFriendRequest();

        clearBrowserCookies();
        clearBrowserLocalStorage();
        refresh();
        open("/");
        FriendsPage page = new LoginPage()
                .login(bot.login(), bot.password())
                .goToFriendsPage()
                .acceptRequest(friend.username());
        refresh();
        page.verifyFriendExists(friend.username());
    }
}
