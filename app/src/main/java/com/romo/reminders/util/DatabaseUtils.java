package com.romo.reminders.util;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.romo.reminders.data.Reminder;
import com.romo.reminders.data.ReminderContract.ReminderEntry;
import com.romo.reminders.data.ReminderCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    @SuppressWarnings("ConstantConditions")
    public static Reminder addReminder(@NonNull Context context, @NonNull Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(ReminderEntry.COLUMN_TITLE, reminder.getTitle());
        values.put(ReminderEntry.COLUMN_PRIORITY, reminder.getPriority());
        values.put(ReminderEntry.COLUMN_COMPLETED, reminder.isCompleted() ? 1 : 0);

        Uri uri = context.getContentResolver().insert(ReminderEntry.CONTENT_URI, values);

        int reminderId = Integer.parseInt(uri.getLastPathSegment());

        return getReminder(context, reminderId);
    }

    public static Reminder getReminder(@NonNull Context context, int reminderId) {
        ReminderCursorWrapper cursor = new ReminderCursorWrapper(context.getContentResolver()
                .query(ReminderEntry.CONTENT_URI,
                        null,
                        null,
                        new String[]{String.valueOf(reminderId)},
                        null)
        );

        cursor.moveToFirst();

        Reminder reminder = cursor.getReminder();

        cursor.close();

        return reminder;
    }

    public static List<Reminder> getReminders(@NonNull Context context) {
        List<Reminder> reminderList = new ArrayList<>();

        ReminderCursorWrapper cursor = new ReminderCursorWrapper(context.getContentResolver()
                .query(ReminderEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        ReminderEntry.COLUMN_LIST_POSITION)
        );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                reminderList.add(cursor.getReminder());
            }
        }

        cursor.close();

        return reminderList;
    }

    public static void saveReminder(@NonNull Context context, @NonNull Reminder reminder) {
        Uri uri = Uri.withAppendedPath(ReminderEntry.CONTENT_URI, String.valueOf(reminder.getId()));

        ContentValues values = new ContentValues();
        values.put(ReminderEntry.COLUMN_ID, reminder.getId());
        values.put(ReminderEntry.COLUMN_TITLE, reminder.getTitle());
        values.put(ReminderEntry.COLUMN_PRIORITY, reminder.getPriority());
        values.put(ReminderEntry.COLUMN_COMPLETED, reminder.isCompleted() ? 1 : 0);
        values.put(ReminderEntry.COLUMN_LIST_POSITION, reminder.getListPosition());

        context.getContentResolver()
                .update(uri,
                        values,
                        null,
                        new String[]{String.valueOf(reminder.getId())});
    }

    public static void deleteReminder(@NonNull Context context, int reminderId) {
        Uri uri = Uri.withAppendedPath(ReminderEntry.CONTENT_URI, String.valueOf(reminderId));
        context.getContentResolver().delete(uri, null, null);
    }
}
