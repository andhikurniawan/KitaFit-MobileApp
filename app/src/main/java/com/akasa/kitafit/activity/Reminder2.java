package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.adapter.AlarmReminderContract;
import com.akasa.kitafit.adapter.AlarmReminderDbHelper;

//public class Reminder2 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
//    private Toolbar mToolbar;
//    AlarmCursorAdapter mCursorAdapter;
//    AlarmReminderDbHelper alarmReminderDbHelper = new AlarmReminderDbHelper(this);
//    ListView reminderListView;
//    ProgressDialog prgDialog;
//    private static final int VEHICLE_LOADER = 0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reminder2);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitle(R.string.app_name);
//
//
//        reminderListView = (ListView) findViewById(R.id.list);
//
//        mCursorAdapter = new AlarmCursorAdapter(this, null);
//        reminderListView.setAdapter(mCursorAdapter);
//
//        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                Intent intent = new Intent(Reminder2.this, AddReminder2.class);
//
//                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);
//
//                // Set the URI on the data field of the intent
//                intent.setData(currentVehicleUri);
//
//                startActivity(intent);
//
//            }
//        });
//        getSupportLoaderManager().initLoader(VEHICLE_LOADER, null,  this);
//
//    }
//
//    @NonNull
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
//        String[] projection = {
//                AlarmReminderContract.AlarmReminderEntry._ID,
//                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE
//
//        };
//
//        return new CursorLoader(this,   // Parent activity context
//                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,   // Provider content URI to query
//                projection,             // Columns to include in the resulting Cursor
//                null,                   // No selection clause
//                null,                   // No selection arguments
//                null);                  // Default sort order
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
//        mCursorAdapter.swapCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//        mCursorAdapter.swapCursor(null);
//    }
//
//    public void GoToAdd(View view) {
//        Intent intent = new Intent(view.getContext(), AddReminder2.class);
//        startActivity(intent);
//    }
//}