package com.romo.reminders.util;

import com.romo.reminders.R;

public class ReminderUtils {

    public static final int MAX_PRIORITY_LEVEL = 3;
    public static final int MIN_PRIORITY_LEVEL = 0;

    public static int getPriorityStripColorRes(int priority) {
        switch (priority) {
            case 0:
                return R.color.priority_none;
            case 1:
                return R.color.priority_low;
            case 2:
                return R.color.priority_med;
            case 3:
                return R.color.priority_high;
            default:
                throw new IllegalArgumentException("Unsupported priority value: " + priority);
        }
    }
}
