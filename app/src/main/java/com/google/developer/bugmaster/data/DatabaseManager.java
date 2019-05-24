package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.developer.bugmaster.data.BugsDbHelper.COLUMN_CLASSIFICATION;
import static com.google.developer.bugmaster.data.BugsDbHelper.COLUMN_DANGER_LEVEL;
import static com.google.developer.bugmaster.data.BugsDbHelper.COLUMN_FRIENDLY_NAME;
import static com.google.developer.bugmaster.data.BugsDbHelper.COLUMN_IMAGE_ASSET;
import static com.google.developer.bugmaster.data.BugsDbHelper.COLUMN_SCIENTIFIC_NAME;
import static com.google.developer.bugmaster.data.BugsDbHelper.TABLE_NAME;

/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private static BugsDbHelper mBugsDbHelper;

    public DatabaseManager(Context context) {
//    private DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public static Cursor queryAllInsects(String sortOrder) {
        //TODO: Implement the query
		//COMPLETED
//        mBugsDbHelper = new DatabaseManager()
		final SQLiteDatabase db = mBugsDbHelper.getReadableDatabase();
		
        return db.query(TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);
    }

    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        //TODO: Implement the query
		//COMPLETED
		final SQLiteDatabase db = mBugsDbHelper.getReadableDatabase();
        return db.query(TABLE_NAME,
                        null,
                        "_id=?",
                        new String[]{id+""},
                        null,
                        null,
                        null);
    }

    public void inserInsectJSONObject(JSONObject obj) throws JSONException {
        final SQLiteDatabase db = mBugsDbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        // Put the insect  into the ContentValues
        contentValues.put(COLUMN_FRIENDLY_NAME, obj.getString("friendlyName"));
        contentValues.put(COLUMN_SCIENTIFIC_NAME, obj.getString("scientificName"));
        contentValues.put(COLUMN_CLASSIFICATION, obj.getString("classification"));
        contentValues.put(COLUMN_IMAGE_ASSET, obj.getString("imageAsset"));
        contentValues.put(COLUMN_DANGER_LEVEL, obj.getString("dangerLevel"));

        db.insert(TABLE_NAME, null, contentValues);
    }
}
//TODO: close helper