package com.google.developer.bugmaster;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.bugmaster.data.BugsDbHelper;
import com.google.developer.bugmaster.data.DatabaseManager;
import com.google.developer.bugmaster.data.Insect;
import com.google.developer.bugmaster.data.InsectRecyclerAdapter;
import com.google.developer.bugmaster.pref.BugsMasterPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>  {

    private static final int INSECT_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String EXTRA_INSECTS = "insects";
    private List<Insect> mInsects;

    // Member variables for the adapter and RecyclerView
    private InsectRecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;

    private ArrayList randomInsects = new ArrayList<Insect>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the RecyclerView to its corresponding view
        mRecyclerView =  (RecyclerView)findViewById(R.id.recycler_view);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new InsectRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

		/*
         Ensure a loader is initialized and active. If the loader doesn't already exist, one is
         created, otherwise the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(INSECT_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                String sortBy = BugsMasterPref.getSort(MainActivity.this);
                if(sortBy.equals(BugsDbHelper.COLUMN_FRIENDLY_NAME)){
                    BugsMasterPref.setSort(MainActivity.this, BugsDbHelper.COLUMN_DANGER_LEVEL+" DESC");
                }
                else {
                    BugsMasterPref.setSort(MainActivity.this, BugsDbHelper.COLUMN_FRIENDLY_NAME);

                }

                getSupportLoaderManager().restartLoader(INSECT_LOADER_ID, null, this);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {
        Intent quizActivity = new Intent(this, QuizActivity.class);
        //list insects
        quizActivity.putParcelableArrayListExtra(QuizActivity.EXTRA_INSECTS, randomInsects);

        //set insect (questionable)
        quizActivity.putExtra(QuizActivity.EXTRA_ANSWER, (Insect)randomInsects.get(new Random().nextInt(randomInsects.size())));


        startActivity(quizActivity);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the journal data
            Cursor mInsectsData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mInsectsData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mInsectsData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                DatabaseManager databaseManager = new DatabaseManager(getContext());

                try {
                    return DatabaseManager.queryAllInsects(/*get pref*/BugsMasterPref.getSort(MainActivity.this));

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage()+" Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mInsectsData = data;
                super.deliverResult(data);
            }
        };



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        randomInsects.clear();
        mAdapter.swapCursor(data);


        for (int i = 0; i<5; i++){
            randomInsects.add(mAdapter.getItem(new Random().nextInt(mAdapter.getItemCount())));

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArray(EXTRA_INSECTS, (List<? extends Parcelable>)mInsects);
    }

}
