package homework_3.model;

import java.util.Objects;

public record Bot(String login, String username, String password) {
    public Bot {
        Objects.requireNonNull(login);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }
}
