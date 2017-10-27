package com.example.saeed.habittracker_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.saeed.habittracker_app.data.CodeContract.CodeEntry;

public class CodeDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = CodeDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "code.db";
    private static final int DATABASE_VERSION = 1;

    public CodeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CODE_TABLE = "CREATE TABLE " + CodeEntry.TABLE_NAME + " ("
                + CodeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CodeEntry.COLUMN_LANGUAGE + " TEXT NOT NULL, "
                + CodeEntry.COLUMN_PRACTICE_HOURS + " INTEGER NOT NULL DEFAULT 0, "
                + CodeEntry.COLUMN_FEELING + " INTEGER NOT NULL DEFAULT 0, "
                + CodeEntry.COLUMN_MODE + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_CODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
