import Schedule.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NonCommand {
    private class User {
        private final Long id;
        private String institute;
        private Integer course;
        private String group;
        private int phase;

        public User(Long id) {
            this.id = id;
            this.phase = 0;
            this.institute = null;
            this.course = null;
            this.group = null;
        }

        public void nextPhase() {
            phase++;
        }

        public Long getId() {
            return id;
        }

        public int getPhase() {
            return phase;
        }

        public String getInstitute() {
            return institute;
        }

        public Integer getCourse() {
            return course;
        }

        public String getGroup() {
            return group;
        }

        public void setInstitute(String institute) {
            this.institute = institute;
        }

        public void setCourse(Integer course) {
            this.course = course;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }

    private List<User> initUsers;
    private DB db;
    private ScheduleParser parser;

    public NonCommand(DB db, ScheduleParser parser) {
        this.db = db;
        this.parser = parser;
        this.initUsers = new ArrayList<>();
    }

    public NonCommandReturn setInstituteAnswer(String userName) {
        String answer =
        """
        Привет %s, я телеграм-бот для получения расписания с сайта МГТУ!
        Прошу, укажи свой институт, курс и группу".
        
        Начнем с института, выбери на клавиатуре свой институт
        """;
        return new NonCommandReturn(answer.formatted(userName), ReplyKeyboardMaker.getInstituteKeyboard());
    }

    private NonCommandReturn setCourseAnswer() {
        String answer = "Выбери свой курс";
        return new NonCommandReturn(answer, ReplyKeyboardMaker.getCourseKeyboard());
    }

    private NonCommandReturn setGroupAnswer() {
        String answer = "Введи название своей группы";
        return new NonCommandReturn(answer, null);
    }

    private NonCommandReturn setEndOfInitAnswer() {
        String answer = "Поздравляю, настройки сохранены! " +
                "Далее для взаимодействия используй один из предложенных вариантов";
        return new NonCommandReturn(answer, ReplyKeyboardMaker.getMainFunctionsKeyboard());
    }

    private NonCommandReturn setWeekScheduleAnswer(String schedule) {
        String answer = "Расписание на эту неделю\n" + schedule;
        return new NonCommandReturn(answer, ReplyKeyboardMaker.getMainFunctionsKeyboard());
    }

    public NonCommandReturn nonCommandExecute(String message, Long chatId, String userName) throws SQLException {
        boolean isUserEx = db.isUserExist(chatId);
        NonCommandReturn ret;
        // Process beginning of init sequence
        switch (message) {
            case "/start", "Сменить настройки" -> {
                // User started init sequence, so we should firstly delete previous info about him
                if (isUserEx) db.delUser(chatId);
                // Add user to the list of users, what are currently going through init sequence
                User user = new User(chatId);
                initUsers.add(user);
            }
            case "Расписание на завтра" -> {

            }
            case "Расписание на неделю" -> {
                String[] user = db.getUserString(chatId).split(" ");
                Schedule sc = this.parser.parse(user[0], Integer.parseInt(user[1]), user[2]);
                ret = this.setWeekScheduleAnswer(sc.getWeekScheduleString());
                return ret;
            }
            case "Расписание на..." -> {

            }
        }
        // If message not matching any possible command, this can be answer to init sequence
        Iterator<User> iterator = this.initUsers.iterator();
        while (iterator.hasNext()) {
            User item = iterator.next();
            if (item.getId().equals(chatId)) {
                switch (item.getPhase()) {
                    case 0 -> {
                        item.nextPhase();
                        ret = this.setInstituteAnswer(userName);
                    }
                    case 1 -> {
                        item.setInstitute(message);
                        item.nextPhase();
                        ret = this.setCourseAnswer();
                    }
                    case 2 -> {
                        item.setCourse(Integer.parseInt(message));
                        item.nextPhase();
                        ret = this.setGroupAnswer();
                    }
                    case 3 -> {
                        item.setGroup(message);
                        db.addUser(item.getId(), item.getInstitute(), item.getCourse(), item.getGroup());
                        iterator.remove();
                        ret = this.setEndOfInitAnswer();
                    }
                    default -> throw new IllegalStateException("Unexpected value in item.getPhase(): " + item.getPhase());
                };
                return ret;
            }
        }
        String answer =
        """
        Я вас не понял :(
        Пожалуйста, воспользуйтесь предлагаемыми функциями!
        """;
        return new NonCommandReturn(answer, ReplyKeyboardMaker.getMainFunctionsKeyboard());
    }
}
