package homework_3.services;

import homework_3.model.Bot;
import homework_3.model.BotType;
import io.github.cdimascio.dotenv.Dotenv;

public class BotFactory {
    public static Bot createBot(BotType botType) {
        final Dotenv dotenv = Dotenv.load();
        return switch (botType) {
            case COMMON -> new Bot(dotenv.get("OK_LOGIN"), dotenv.get("OK_USERNAME"), dotenv.get("OK_PASSWORD"));
            case FRIEND -> new Bot(dotenv.get("FRIEND_LOGIN"), dotenv.get("FRIEND_USERNAME"), dotenv.get("FRIEND_PASSWORD"));
        };
    }
}
