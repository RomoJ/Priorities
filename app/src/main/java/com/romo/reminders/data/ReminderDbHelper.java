package com.romo.reminders.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.romo.reminders.data.ReminderContract.ReminderEntry;

public class ReminderDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Reminders.db";

    private static final int DATABASE_VERSION = 1;

    private static final String INT_TYPE = "INTEGER";

    private static final String STRING_TYPE = "TEXT";

    private static final String BOOLEAN_TYPE = "INTEGER";

    private static final String COMMA_SEP = ", ";

    public ReminderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the reminder entry table
        db.execSQL("CREATE TABLE " + ReminderEntry.TABLE_NAME + " (" +
                   ReminderEntry.COLUMN_ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                   ReminderEntry.COLUMN_TITLE + STRING_TYPE + COMMA_SEP +
                   ReminderEntry.COLUMN_PRIORITY + INT_TYPE + COMMA_SEP +
                   ReminderEntry.COLUMN_COMPLETED + BOOLEAN_TYPE + COMMA_SEP +
                   ReminderEntry.COLUMN_LIST_POSITION + INT_TYPE + ")");

        // Create a trigger that is in charge of assigning list positions to newly inserted reminders
        db.execSQL("CREATE TRIGGER assign_list_position_to_reminder AFTER INSERT ON " + ReminderEntry.TABLE_NAME +
                   " BEGIN UPDATE " + ReminderEntry.TABLE_NAME +
                   " SET " + ReminderEntry.COLUMN_LIST_POSITION + "=(" + "SELECT MAX(" + ReminderEntry.COLUMN_LIST_POSITION + ") FROM " + ReminderEntry.TABLE_NAME + ") + 1" +
                   " WHERE " + ReminderEntry.COLUMN_ID + "=NEW." + ReminderEntry.COLUMN_ID + ";" + " END;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as of version 1
    }
}