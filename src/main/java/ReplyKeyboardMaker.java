import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMaker {
    public static ReplyKeyboardMarkup getInstituteKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("ЕТИ"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("ИМА"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("АФ"));
        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("ИАТ"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getCourseKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("1"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("2"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("3"));
        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("4"));
        KeyboardRow row5 = new KeyboardRow();
        row5.add(new KeyboardButton("5"));
        KeyboardRow row6 = new KeyboardRow();
        row6.add(new KeyboardButton("6"));
        KeyboardRow row7 = new KeyboardRow();
        row7.add(new KeyboardButton("7"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);
        keyboard.add(row6);
        keyboard.add(row7);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMainFunctionsKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Расписание на завтра"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Расписание на неделю"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Расписание на..."));
        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("Сменить настройки"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
