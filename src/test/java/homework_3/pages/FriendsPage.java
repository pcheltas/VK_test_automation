package homework_3.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Represents the Friends page of a social network, providing functionality of searching for friends,
 * sending requests, processing incoming requests, interacting with existing friends.
 * <p>
 * This class encapsulates three main components:
 * <ul>
 *     <li>{@link Search} – searching friends and sending requests</li>
 *     <li>{@link Requests} – processing incoming friend requests</li>
 *     <li>{@link FriendsList} – interacting with existing friends</li>
 * </ul>
 * </p>
 */
public class FriendsPage extends LoadableComponent<FriendsPage> {
    private final Search search;
    private final Requests requests;
    private final FriendsList friendsList;

    public FriendsPage() {
        this.search = new Search();
        this.requests = new Requests();
        this.friendsList = new FriendsList();
    }

    @Override
    protected void load() {
        search.searchInput.shouldBe(visible.because("Searching for friends should be visible on loaded friends page"));
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            search.searchInput.shouldBe(visible.because("Searching for friends should be visible on loaded friends page"));
            friendsList.friendsBlock.shouldBe(visible.because("Friends block should be visible on loaded friends page"));
        } catch (Exception e) {
            throw new Error("Friends page was not loaded properly: " + e.getMessage());
        }
    }

    /**
     * Searches for a friend by their name.
     *
     * @param name the name of the friend to search for.
     * @return currant {@code FriendsPage} instance.
     * @throws InterruptedException if the thread is interrupted during the search.
     */
    public FriendsPage searchFriendByName(String name) throws InterruptedException {
        search.searchFriend(name);
        return this;
    }

    /**
     * Sends a friend request to the first user in the search results.
     *
     * @return the current {@code FriendsPage} instance.
     */
    public FriendsPage sendFriendRequest() {
        search.sendFriendRequest();
        return this;
    }

    /**
     * Accepts a friend request from the specified user.
     *
     * @param friendName the name of the user whose request should be accepted.
     * @return the current {@code FriendsPage} instance.
     * @throws InterruptedException if the thread is interrupted during the operation.
     */
    public FriendsPage acceptRequest(String friendName) throws InterruptedException {
        requests.acceptRequest(friendName);
        return this;
    }

    /**
     * Verifies that a friend with the specified name exists in the friends list.
     *
     * @param friendName the name of the friend to check.
     * @return the current {@code FriendsPage} instance.
     * @throws InterruptedException if the thread is interrupted during the search.
     */
    public FriendsPage verifyFriendExists(String friendName) throws InterruptedException {
        friendsList.searchForFriend(friendName).shouldBe(exist.because("Friend card should exist to verify it"));
        return this;
    }

    /**
     * A helper class encapsulating the functionality related to searching for friends
     * and sending friend requests.
     */
    private class Search {
        private final SelenideElement searchInput = $x("//input[@type='search']");
        private final ElementsCollection searchResults = $$x("//div[contains(@class, 'gs_result_list')]");
        private final String anchor = ".//a";

        /**
         * Searches for a friend by entering their name into the search field and pressing Enter.
         *
         * @param friend the name of the friend to search for.
         * @throws InterruptedException if the thread is interrupted during the search.
         */
        public void searchFriend(String friend) throws InterruptedException {
            searchInput.shouldBe(visible.because("Should be search input to find a friend"))
                    .shouldBe(enabled.because("Input should be enabled to click on it and focus"))
                    .click();
            Thread.sleep(3000);             // для ожидания фокусировки строки поиска после нажатия.
            // Использование неявных ожиданий или отсутствие предварительного клика вообще
            // влекут за собой бездействие при нажатии Enter (поиск не начинается).
            // Для использования явных ожиданий непонятно чего ждать. Можно было бы ждать
            // появления истории поиска, но на практике с sleep() при прогоне теста она (история) не
            // появляется, а поиск работает. Как исправить хз, знаю, что плохая практика
            searchInput.shouldBe(focused.because("Input should be focused to set value in it"))
                    .setValue(friend)
                    .pressEnter();
        }

        /**
         * Sends a friend request to the first user in the search results.
         * Assumes that the search has already been performed and results are available.
         */
        public void sendFriendRequest() {
            searchResults.shouldBe(sizeGreaterThan(0).because("Should be list of found people to send friend request"))
                    .first()
                    .$$x(anchor)
                    .findBy(text("Добавить в друзья"))
                    .shouldBe(visible.because("Button needed to send friend request"))
                    .click();
        }
    }

    /**
     * A helper class encapsulating the functionality related to managing incoming friend requests.
     */
    private class Requests {
        private final SelenideElement requestBlock = $x("//friends-requests-block");
        private final String requestItems = ".//div[contains(@data-l, 'targetUserId')]";
        private final String acceptButton = ".//span[@role='button' and contains(@data-l, 'accept')]";

        /**
         * Accepts a friend request from the specified user.
         *
         * @param friendName the name of the user whose request should be accepted.
         * @throws InterruptedException if the thread is interrupted during the operation.
         */
        public void acceptRequest(String friendName) throws InterruptedException {
            Thread.sleep(5000);           //При отсутствии sleep() нажатие на кнопку не принимает заявку в друзья. Так же, как и
            // со строкой поиска, неявные ожидания не помогают. Ощущение, как будто скрипт
            // на обработку события не успевает замаппиться на кнопку (если не это, то без
            // понятия, что происходит). Для использования явных ожиданий все так же непонятно
            // чего ждать. Тоже хз как исправить
            requestBlock.$$x(requestItems)
                    .shouldBe(sizeGreaterThan(0).because("Friendship requests needed to accept request"))
                    .findBy(text(friendName))
                    .$x(acceptButton)
                    .shouldBe(visible.because("Accept request button needed to accept request"))
                    .click();
        }
    }

    /**
     * A helper class encapsulating the functionality related to interacting with friends list.
     */
    private class FriendsList {
        private final SelenideElement friendsBlock = $x("//div[@id='hook_Block_MyFriendsSquareCardsPagingB']");
        private final String friendCards = ".//li";

        /**
         * Searches for a friend in the current friends list by their name.
         *
         * @param friendName the name of the friend to search for.
         * @return the {@link SelenideElement} representing the found friend.
         */
        public SelenideElement searchForFriend(String friendName) {
            return friendsBlock.$$x(friendCards)
                    .shouldHave(sizeGreaterThan(0).because("Friends needed to find a specific friend"))
                    .findBy(text(friendName));
        }


    }
}