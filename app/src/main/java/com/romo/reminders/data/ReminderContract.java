package com.romo.reminders.data;

/**
 * The contract used to create the database to save reminders locally.
 */
public class ReminderContract {

    public static final class ReminderEntry {

        public static final String TABLE_NAME = "reminder";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_LIST_POSITION = "list_position";
    }
}