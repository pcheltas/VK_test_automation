package homework_3.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FriendsPage {
    private final Search search;
    private final Requests requests;
    private final FriendsList friendsList;

    public FriendsPage() {
        this.search = new Search();
        this.requests = new Requests(this);
        this.friendsList = new FriendsList(this);
    }

    public FriendsList friendsList() {
        return friendsList;
    }

    public Requests requests() {
        return requests;
    }

    public Search search() {
        return search;
    }

    public FriendsPage refreshPage() throws InterruptedException {
        Selenide.refresh();
        Thread.sleep(1000);
        return this;
    }

    public static class Search {
        private final SelenideElement searchInput = $("#hook_Block_UserFriendsMenu input[type=search]");
        private final ElementsCollection searchResults = $$("#hook_Block_MyFriendsGlobalSearchPagingB .gs_result_list");

        public Search searchFriend(String friend) throws InterruptedException {
            searchInput.click();
            Thread.sleep(1000);
            searchInput.setValue(friend).pressEnter();
            return this;
        }

        public Search sendFriendRequest() {
            searchResults.first()
                    .$$("a").findBy(text("Добавить в друзья"))
                    .click();
            return this;
        }
    }

    public static class Requests {
        private final ElementsCollection requestItems = $$("#hook_Block_UserFriendshipRequestsList div[data-l*='targetUserId']");
        private final String acceptButton = "span[data-l*='friendshipAction'][data-l*='accept']";
        private final FriendsPage parent;

        public Requests(FriendsPage parent) {
            this.parent = parent;
        }

        public FriendsPage acceptRequest(String friendName) throws InterruptedException {
            Thread.sleep(5000);
            requestItems.findBy(text(friendName))
                    .$(acceptButton)
                    .click();
            return parent;
        }
    }

    public static class FriendsList {
        private final FriendsPage parent;
        private final ElementsCollection friendCards = $$("ul.ugrid_cnt li.ugrid_i");

        public FriendsList(FriendsPage parent) {
            this.parent = parent;
        }

        public SelenideElement searchForFriend(String friendName) throws InterruptedException {
            Thread.sleep(3000);
            return friendCards
                    .shouldHave(sizeGreaterThan(0))
                    .findBy(text(friendName));
        }

        public FriendsPage verifyFriendExists(String friendName) throws InterruptedException {
            searchForFriend(friendName).shouldBe(visible);
            return parent;
        }
    }
}