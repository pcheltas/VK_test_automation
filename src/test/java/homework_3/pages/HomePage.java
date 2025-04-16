package homework_3.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
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
public class HomePage extends LoadableComponent<HomePage> {
    private final Navigation navigation;
    private final Feed feed;
    private final String activeClassname = "__ac";

    public HomePage() {
        this.navigation = new Navigation();
        this.feed = new Feed();
    }

    @Override
    protected void load() {
        navigation.userProfileLink.shouldBe(visible.because("User profile link should be visible on loaded home page"));
    }

    @Override
    protected void isLoaded() throws Error {
        navigation.userProfileLink.shouldBe(visible.because("User profile link should be visible on loaded home page"));
        feed.feedItems.shouldHave(sizeGreaterThan(0).because("Feed items should exist on loaded home page"));
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
        return page(FriendsPage.class).get();
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
        feed.getFeedItems().shouldHave(sizeGreaterThan(0).because("To verify feed is not empty should be at least one item"));
        return this;
    }

    /**
     * Verifies that a navigation link with the specified name is marked as active.
     * <p>
     * Checks for the presence of active state CSS class on the target navigation link.
     *
     * @param name The display text of the navigation link to verify
     * @return current {@code HomePage} instance for method chaining
     */
    public HomePage verifyNavLinkByNameIsActive(String name) {
        navigation.getNavLinkByName(name)
                .shouldHave(cssClass(activeClassname));
        return this;
    }

    /**
     * Verifies that the user profile link displays the expected username.
     * <p>
     *     Validates user authentication state by checking visible username in profile section.
     * </p>
     * @param name Expected username text to verify
     * @return current {@code HomePage} instance for method chaining
     */
    public HomePage verifyUserProfileLinkContainsUsername(String name) {
        getUserProfileLink().shouldHave(text(name));
        return this;
    }

    /**
     * A helper class encapsulating all navigation-related functionality.
     */
    private class Navigation {
        private final SelenideElement userProfileLink = $x("//a[contains(@data-l, 'userPage')]");
        private final SelenideElement friendsLink = $x("//a[contains(@data-l, 'userFriend')]");
        private final String datalFormatter = "//a[contains(@data-l, '%s')]";

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
            return $x(String.format(datalFormatter, name));
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