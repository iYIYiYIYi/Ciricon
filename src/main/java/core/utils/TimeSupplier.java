package core.utils;

import java.util.Calendar;

public class TimeSupplier {

    public static long now() {
        return Calendar.getInstance().getTimeInMillis();
    }



}
