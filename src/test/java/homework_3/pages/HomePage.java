package homework_3.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Represents the main home page of a social network, providing access to navigation
 * and feed functionality.
 * <p>
 * The page consists of two main components:
 * <ul>
 *   <li>{@link Navigation} - handles all navigation-related actions (profile, friends, etc.)</li>
 *   <li>{@link Feed} - manages interactions with the news feed</li>
 * </ul>
 * </p>
 */
public class HomePage {
    private final Navigation navigation;
    private final Feed feed;

    public HomePage() {
        this.navigation = new Navigation();
        this.feed = new Feed();
    }

    /**
     * Retrieves the user profile link element.
     *
     * @return {@code SelenideElement} representing the user profile link
     */
    public SelenideElement getUserProfileLink() {
        return navigation.getUserProfileLink();
    }

    /**
     * Navigates to the friends page.
     *
     * @return new instance of {@code FriendsPage}
     */
    public FriendsPage goToFriendsPage() {
        navigation.goToFriendsPage();
        return page(FriendsPage.class);
    }

    /**
     * Verifies that the news feed contains at least one item.
     *
     * @return current {@code HomePage} instance for method chaining
     */
    public HomePage verifyFeedIsNotEmpty() {
        feed.getFeedItems().shouldHave(sizeGreaterThan(0));
        return this;
    }

    /**
     * A helper class encapsulating all navigation-related functionality.
     */
    private class Navigation {
        private final SelenideElement userProfileLink = $x("//a[contains(@data-l, 'userPage')]");
        private final SelenideElement friendsLink = $x("//a[contains(@data-l, 'userFriend')]");

        /**
         * Gets the user profile link element.
         *
         * @return {@code SelenideElement} representing the user profile link
         */
        public SelenideElement getUserProfileLink() {
            return userProfileLink;
        }

        /**
         * Navigates to the friends page by clicking the friends link.
         */
        public void goToFriendsPage() {
            friendsLink.shouldBe(visible.because("Should be a button in navbar to go to the friends page"))
                    .click();
        }
    }

    /**
     * A helper class encapsulating news feed interactions.
     */
    private class Feed {
        private final ElementsCollection feedItems = $$x("//*[contains(@class, 'feed-w')]");

        /**
         * Gets all visible feed items.
         *
         * @return {@code ElementsCollection} containing all feed items
         */
        public ElementsCollection getFeedItems() {
            return feedItems;
        }

    }
}