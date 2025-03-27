package homework_3.tests;

import homework_3.model.Bot;
import homework_3.model.BotType;
import homework_3.pages.LoginPage;
import homework_3.services.BotFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

@DisplayName("Friend page test")
public class FriendTest extends BasicTest {

    @DisplayName("Successful friend adding")
    @Test
    void user_shouldAddFriend() throws InterruptedException {
        final Bot friend = BotFactory.createBot(BotType.FRIEND);
        new LoginPage()
                .open()
                .login(friend.login(), friend.password())
                .navigation()
                .goToFriendsPage()
                .search()
                .searchFriend(bot.username())
                .sendFriendRequest();

        clearBrowserCookies();
        clearBrowserLocalStorage();
        refresh();

        new LoginPage()
                .open()
                .login(bot.login(), bot.password())
                .navigation()
                .goToFriendsPage()
                .requests()
                .acceptRequest(friend.username())
                .refreshPage()
                .friendsList()
                .verifyFriendExists(friend.username());
    }
}
