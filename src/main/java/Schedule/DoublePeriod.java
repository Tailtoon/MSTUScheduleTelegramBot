package Schedule;

public class DoublePeriod {
    private int num;
    private String name;
    private String teacher;
    private String cab;

    public DoublePeriod(int num, String name, String teacher, String cab) {
        this.num = num;
        this.name = name;
        this.teacher = teacher;
        this.cab = cab;
    }

    public final int getNum() {
        return this.num;
    }

    public final String getName() {
        return this.name;
    }

    public final String getTeacher() {
        return this.teacher;
    }

    public final String getCab() {
        return this.cab;
    }
}
