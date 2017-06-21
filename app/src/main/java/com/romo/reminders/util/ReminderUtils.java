package com.romo.reminders.util;

import com.romo.reminders.R;

public class ReminderUtils {

    public static int getPriorityStripColorRes(int priority) throws IllegalArgumentException {
        switch (priority) {
            case 0:
                return R.color.priority_none;
            case 1:
                return R.color.priority_high;
            case 2:
                return R.color.priority_med;
            case 3:
                return R.color.priority_low;
            default:
                throw new IllegalArgumentException("Unsupported priority value: " + priority);
        }
    }
}
