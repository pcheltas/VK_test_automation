package homework_3.model;

import java.util.Objects;

/**
 * Represents a bot entity with authentication credentials.
 * <p>
 * This immutable record class stores the essential bot information required for authentication
 * </p>
 * <p>
 * The constructor performs null checks on all parameters to ensure data integrity.
 * </p>
 *
 * @param login the bot's login identifier
 * @param username the bot's display name
 * @param password the bot's authentication password
 * @throws NullPointerException if any of the parameters are null
 */
public record Bot(String login, String username, String password) {
    public Bot {
        Objects.requireNonNull(login);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
    }
}
