import Schedule.ScheduleParser;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ScheduleBot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final NonCommand nonCommand;
    private final DB db;
    private final ScheduleParser parser;
    private static Map<Long, Settings> userSettings;

    public ScheduleBot(String botName, String botToken, String dbname, String dbusername, String dbpass)
        throws SQLException {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.db = new DB(dbname, dbusername, dbpass);

        this.parser = new ScheduleParser();

        this.nonCommand = new NonCommand(db, parser);

        register(new HelpCommand("help", "Помощь"));
//        register(new StartCommand("start", "Начало"));
//        userSettings = new HashMap<>();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId(); // Equal to userId, unless used in groups
        String userName = getUserName(msg);

        try {
            NonCommandReturn ret = nonCommand.nonCommandExecute(msg.getText(), chatId, userName);
            if (ret.getKeyboard() != null) this.setAnswer(chatId, userName, ret.getAnswer(), ret.getKeyboard());
            else this.setAnswer(chatId, userName, ret.getAnswer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public static Settings getUserSettings(Long chatId) {
        return
    }*/

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer (Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        answer.setReplyMarkup(new ReplyKeyboardRemove(true));
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            // TODO: make a log class to print and save info about all actions and errors
        }
    }

    private void setAnswer (Long chatId, String userName, String text, ReplyKeyboard replyKeyboard) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        answer.setReplyMarkup(replyKeyboard);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            // TODO: make a log class to print and save info about all actions and errors
        }
    }
}
