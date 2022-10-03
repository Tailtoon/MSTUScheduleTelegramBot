import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MSTUScheduleApplication {

    private static final String pathToEnv = "";

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.configure().directory(pathToEnv).load();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ScheduleBot(dotenv.get("BOT_NAME"), dotenv.get("BOT_TOKEN")));
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO: make a log class to print and save info about all actions and errors
        }
    }
}
