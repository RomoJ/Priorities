package com.romo.reminders.data;

import android.net.Uri;

/**
 * The contract used to create the database to save reminders locally.
 */
public class ReminderContract {

    public static final String AUTHORITY = "com.romo.reminders";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_REMINDERS = "reminders";

    public static final class ReminderEntry {

        public static final String TABLE_NAME = "reminder";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REMINDERS).build();

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_LIST_POSITION = "list_position";
    }
}