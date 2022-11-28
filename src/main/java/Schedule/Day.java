package Schedule;

import java.util.List;

public class Day {
    private List<DoublePeriod> dps;

    public Day(List<DoublePeriod> dps) {
        this.dps = dps;
    }

    public final List<DoublePeriod> getDoublePeriods() {
        return this.dps;
    }
}
