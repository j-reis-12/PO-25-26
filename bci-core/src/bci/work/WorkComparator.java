package bci.work;

import java.util.Comparator;

public class WorkComparator {
    public static Comparator<Work> byId() {
        return Comparator.comparingInt(Work::getId);
    }

    public static Comparator<Work> byTitle() {
        return Comparator.comparing(Work::getTitle, String.CASE_INSENSITIVE_ORDER);
    }

}
