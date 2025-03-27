package homework_3.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {
    private final Navigation navigation;
    private final Feed feed;

    public HomePage() {
        this.navigation = new Navigation();
        this.feed = new Feed();
    }

    public Navigation navigation() {
        return navigation;
    }

    public Feed feed() {
        return feed;
    }

    public static class Navigation {
        private final SelenideElement userProfileLink = $("#hook_Block_Navigation a[data-l*=userPage]");
        private final SelenideElement friendsLink = $("#hook_Block_Navigation a[data-l*=userFriend]");

        public SelenideElement getUserProfileLink() {
            return userProfileLink;
        }

        public FriendsPage goToFriendsPage() {
            friendsLink.click();
            return page(FriendsPage.class);
        }
    }

    public static class Feed {
        private final ElementsCollection feedItems = $$(".feed-w");

        public ElementsCollection items() {
            return feedItems;
        }

        public Feed shouldNotBeEmpty() {
            feedItems.shouldHave(sizeGreaterThan(0));
            return this;
        }
    }
}