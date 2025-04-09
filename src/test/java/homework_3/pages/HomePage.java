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
     * Finds a navigation link by its name.
     *
     * @param name the data-l attribute value identifying the navigation link
     * @return {@code SelenideElement} representing the found navigation link
     */
    public SelenideElement getNavLinkByName(String name) {
        return navigation.getNavLinkByName(name);
    }

    /**
     * Navigates to a page by clicking on the corresponding navigation link.
     *
     * @param name the data-l attribute value identifying the page to navigate to
     * @return current {@code HomePage} instance for method chaining
     */
    public HomePage goToPageByName(String name) {
        navigation.goToPageByName(name);
        return this;
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

        /**
         * Finds a navigation link by its data-l attribute value.
         *
         * @param name the data-l attribute value to search for
         * @return {@code SelenideElement} representing the found navigation link
         */
        public SelenideElement getNavLinkByName(String name) {
            return $x(String.format("//a[contains(@data-l, '%s')]", name));
        }

        /**
         * Navigates to a page by clicking on the corresponding navigation link.
         *
         * @param name the data-l attribute value identifying the page to navigate to
         */
        public void goToPageByName(String name) {
            getNavLinkByName(name)
                    .shouldBe(visible.because("Should be a button in navbar to go to the given page"))
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