package com.example.saeed.habittracker_app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.saeed.habittracker_app.data.CodeContract.CodeEntry;
import com.example.saeed.habittracker_app.data.CodeDbHelper;

public class HabitActivity extends AppCompatActivity {
    private CodeDbHelper mCodeDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo();
        mCodeDbHelper = new CodeDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabase();
    }

    private Cursor displayDatabaseInfo() {

        CodeDbHelper mCodeDbHelper = new CodeDbHelper(this);
        SQLiteDatabase db = mCodeDbHelper.getReadableDatabase();

        String[] projection = {CodeEntry._ID,
                CodeEntry.COLUMN_LANGUAGE,
                CodeEntry.COLUMN_PRACTICE_HOURS,
                CodeEntry.COLUMN_FEELING,
                CodeEntry.COLUMN_MODE};

        Cursor cursor = db.query(CodeEntry.TABLE_NAME, projection,
                null, null,
                null, null, null);
        return cursor;
    }

    private void displayDatabase() {

        Cursor cursor = displayDatabaseInfo();
        TextView displayView = (TextView) findViewById(R.id.text_view_code_record);

        try {

            displayView.setText("Code database contains: " + cursor.getCount() + " code history \n\n");
            displayView.append(CodeEntry._ID + "-" +
                    CodeEntry.COLUMN_LANGUAGE + "-" +
                    CodeEntry.COLUMN_PRACTICE_HOURS + "-" +
                    CodeEntry.COLUMN_FEELING + "-" +
                    CodeEntry.COLUMN_MODE + "\n");

            int idColumnIndex = cursor.getColumnIndex(CodeEntry._ID);
            int languageNameColumnIndex = cursor.getColumnIndex(CodeEntry.COLUMN_LANGUAGE);
            int practiceHoursColumnIndex = cursor.getColumnIndex(CodeEntry.COLUMN_PRACTICE_HOURS);
            int feelingColumnIndex = cursor.getColumnIndex(CodeEntry.COLUMN_FEELING);
            int modeColumnIndex = cursor.getColumnIndex(CodeEntry.COLUMN_MODE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentLanguageName = cursor.getString(languageNameColumnIndex);
                String currentPracticeHours = cursor.getString(practiceHoursColumnIndex);
                String currentFeeling = String.valueOf(cursor.getInt(feelingColumnIndex));
                String currentMode = String.valueOf(cursor.getInt(modeColumnIndex));
                displayView.append("\n" + currentId + "-" + currentLanguageName + "-" + currentPracticeHours + "-" +
                        currentFeeling + "-" + currentMode);
            }

        } finally {
            cursor.close();
        }
    }


    private void insertCode() {
        SQLiteDatabase db = mCodeDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CodeEntry.COLUMN_LANGUAGE, "JAVA");
        values.put(CodeEntry.COLUMN_PRACTICE_HOURS, "7");
        values.put(CodeEntry.COLUMN_FEELING, CodeEntry.FEELING_DUBIOUS);
        values.put(CodeEntry.COLUMN_MODE, CodeEntry.MODE_OFFLINE);


        long newRowId = db.insert(CodeEntry.TABLE_NAME, null, values);
    }

    private void deleteDataBase() {

        Context context = HabitActivity.this;
        context.deleteDatabase("code.db");
        mCodeDbHelper = new CodeDbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertCode();
                displayDatabaseInfo();
                recreate();
                return true;
            case R.id.action_delete_all_entries:
                displayDatabaseInfo();
                deleteDataBase();
                recreate();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}