import java.util.List;

public class Schedule {
    private List<Day> days;

    public Schedule(List<Day> days) {
        this.days = days;
    }

    public final List<Day> getDays() {
        return this.days;
    }

    public final void printSchedule() {
        int i = 0;
        for (Day day : this.days) {
            String dayName = switch (i) {
                case 0 -> dayName = "Понедельник";
                case 1 -> dayName = "Вторник";
                case 2 -> dayName = "Среда";
                case 3 -> dayName = "Четверг";
                case 4 -> dayName = "Пятница";
                case 5 -> dayName = "Суббота";
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            System.out.println(dayName);
            for (DoublePeriod dp : day.getDoublePeriods()) {
                int num = dp.getNum();
                String name = dp.getName();
                String teacher = dp.getTeacher();
                String cab = dp.getCab();
                System.out.println(num + " " + name + " " + teacher + " " + cab);
            }
            System.out.println("-------------------------");
            i++;
        }
    }
}
