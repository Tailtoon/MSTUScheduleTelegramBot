import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class NonCommandReturn {
    private final String answer;
    private final ReplyKeyboardMarkup keyborad;

    public NonCommandReturn(String answer, ReplyKeyboardMarkup keyborad) {
        this.answer = answer;
        this.keyborad = keyborad;
    }

    public String getAnswer() {
        return answer;
    }

    public ReplyKeyboardMarkup getKeyboard() {
        return keyborad;
    }
}
