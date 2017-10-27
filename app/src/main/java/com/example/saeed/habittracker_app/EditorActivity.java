package com.example.saeed.habittracker_app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saeed.habittracker_app.data.CodeContract.CodeEntry;
import com.example.saeed.habittracker_app.data.CodeDbHelper;

public class EditorActivity extends AppCompatActivity {
    private CodeDbHelper mCodeDbHelper;
    private EditText mLanguageNameEditText;
    private EditText mCodePracticeHoursEditText;
    private Spinner mModeSpinner;
    private Spinner mFeelingSpinner;
    private int mFeeling = CodeEntry.FEELING_DUBIOUS;
    private int mMode = CodeEntry.MODE_OFFLINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mLanguageNameEditText = (EditText) findViewById(R.id.edit_code_language_name);
        mCodePracticeHoursEditText = (EditText) findViewById(R.id.edit_code_practice_hours);
        mModeSpinner = (Spinner) findViewById(R.id.spinner_mode);
        mFeelingSpinner = (Spinner) findViewById(R.id.spinner_feeling);
        setupFeelingSpinner();
        setupModeSpinner();
    }

    private void setupFeelingSpinner() {
        ArrayAdapter feelingSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_feeling_options, android.R.layout.simple_spinner_item);

        feelingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mFeelingSpinner.setAdapter(feelingSpinnerAdapter);
        mFeelingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.feeling_dubious))) {
                        mFeeling = CodeEntry.FEELING_DUBIOUS;
                    } else if (selection.equals(getString(R.string.feeling_good))) {
                        mFeeling = CodeEntry.FEELING_GOOD;
                    } else {
                        mFeeling = CodeEntry.FEELING_CONFIDENT;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFeeling = CodeEntry.FEELING_DUBIOUS;
            }
        });
    }

    private void setupModeSpinner() {
        ArrayAdapter modeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_mode_options, android.R.layout.simple_spinner_item);
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mModeSpinner.setAdapter(modeSpinnerAdapter);
        mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.mode_offline))) {
                        mMode = CodeEntry.MODE_OFFLINE;
                    } else {
                        mMode = CodeEntry.MODE_ONLINE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMode = CodeEntry.MODE_OFFLINE;
            }
        });
    }

    private void insertCode() {
        String languageNameString = mLanguageNameEditText.getText().toString().trim();
        int mCodePracticeHoursString = Integer.parseInt(mCodePracticeHoursEditText.getText().toString().trim());

        CodeDbHelper mCodeDbHelper = new CodeDbHelper(this);
        SQLiteDatabase db = mCodeDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CodeEntry.COLUMN_LANGUAGE, languageNameString);
        values.put(CodeEntry.COLUMN_PRACTICE_HOURS, mCodePracticeHoursString);
        values.put(CodeEntry.COLUMN_FEELING, mFeeling);
        values.put(CodeEntry.COLUMN_MODE, mMode);

        db.insert(CodeEntry.TABLE_NAME, null, values);
        Toast.makeText(this, "one column added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertCode();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
