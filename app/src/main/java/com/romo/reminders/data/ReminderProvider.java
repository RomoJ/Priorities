package com.romo.reminders.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.romo.reminders.data.ReminderContract.AUTHORITY;
import static com.romo.reminders.data.ReminderContract.PATH_REMINDERS;
import static com.romo.reminders.data.ReminderContract.ReminderEntry;

public class ReminderProvider extends ContentProvider {

    private static final int REMINDERS = 100;
    private static final int REMINDER_WITH_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_REMINDERS, REMINDERS);
        uriMatcher.addURI(AUTHORITY, PATH_REMINDERS + "/#", REMINDER_WITH_ID);
    }

    private ReminderDbHelper reminderDbHelper;

    @Override
    public boolean onCreate() {
        reminderDbHelper = new ReminderDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = reminderDbHelper.getReadableDatabase();

        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case REMINDERS:
                retCursor = db.query(ReminderEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                retCursor = db.query(ReminderEntry.TABLE_NAME,
                        projection,
                        "_id = ?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = reminderDbHelper.getWritableDatabase();

        Uri retUri;

        switch (uriMatcher.match(uri)) {
            case REMINDERS:
                long id = db.insert(ReminderEntry.TABLE_NAME, null, values);

                if (id >= 0) {
                    retUri = ContentUris.withAppendedId(ReminderEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = reminderDbHelper.getWritableDatabase();

        int remindersDeleted;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                remindersDeleted = db.delete(ReminderEntry.TABLE_NAME, "_id = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (remindersDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return remindersDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = reminderDbHelper.getWritableDatabase();

        int remindersUpdated;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                remindersUpdated = db.update(ReminderEntry.TABLE_NAME, values, "_id = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (remindersUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return remindersUpdated;
    }
}