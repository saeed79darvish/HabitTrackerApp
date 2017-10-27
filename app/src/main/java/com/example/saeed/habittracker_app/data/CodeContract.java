package com.example.saeed.habittracker_app.data;

import android.provider.BaseColumns;


public final class CodeContract {
    private CodeContract() {
    }

    public static abstract class CodeEntry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "code";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_PRACTICE_HOURS = "practice_hours";
        public static final String COLUMN_FEELING = "feeling";
        public static final String COLUMN_MODE = "mode";

        public static final int FEELING_DUBIOUS = 0;
        public static final int FEELING_GOOD = 1;
        public static final int FEELING_CONFIDENT = 2;

        public static final int MODE_OFFLINE = 0;
        public static final int MODE_ONLINE = 1;
    }
}
