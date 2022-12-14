import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class HelpCommand extends ServiceCommand {

    public HelpCommand(String id, String description) {
        super(id, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        String text = "Help";
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, text);
    }
}
