package homework_3.services;

import homework_3.model.Bot;
import homework_3.model.BotType;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * A factory class for creating different types of bot instances.
 * <p>
 * This factory reads bot credentials from environment variables (.env file) and creates
 * configured {@link Bot} instances based on the specified {@link BotType}.
 * </p>
 */
public class BotFactory {

    /**
     * Creates a new bot instance of the specified type with credentials from environment variables.
     *
     * @param botType the type of bot to create
     * @return a new configured {@link Bot} instance
     */
    public static Bot createBot(BotType botType) {
        final Dotenv dotenv = Dotenv.load();
        return switch (botType) {
            case COMMON -> new Bot(dotenv.get("OK_LOGIN"), dotenv.get("OK_USERNAME"), dotenv.get("OK_PASSWORD"));
            case FRIEND -> new Bot(dotenv.get("FRIEND_LOGIN"), dotenv.get("FRIEND_USERNAME"), dotenv.get("FRIEND_PASSWORD"));
        };
    }
}
