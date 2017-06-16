package com.romo.reminders.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import static com.romo.reminders.data.ReminderContract.ReminderEntry.COLUMN_COMPLETED;
import static com.romo.reminders.data.ReminderContract.ReminderEntry.COLUMN_ID;
import static com.romo.reminders.data.ReminderContract.ReminderEntry.COLUMN_LIST_POSITION;
import static com.romo.reminders.data.ReminderContract.ReminderEntry.COLUMN_PRIORITY;
import static com.romo.reminders.data.ReminderContract.ReminderEntry.COLUMN_TITLE;

/**
 * Recreates Reminder objects when given the appropriate cursor
 */
public class ReminderCursorWrapper extends CursorWrapper {

    public ReminderCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Reminder getReminder() {
        return new Reminder(
                getInt(getColumnIndex(COLUMN_ID)),
                getString(getColumnIndex(COLUMN_TITLE)),
                getInt(getColumnIndex(COLUMN_PRIORITY)),
                getInt(getColumnIndex(COLUMN_COMPLETED)) != 0,
                getInt(getColumnIndex(COLUMN_LIST_POSITION))

        );
    }
}