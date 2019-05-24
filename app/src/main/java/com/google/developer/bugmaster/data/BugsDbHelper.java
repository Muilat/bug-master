package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.developer.bugmaster.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String     TABLE_NAME = "insects";
    // Table columns
	
    public static final String      COLUMN_ID = "_id";
    public static final String      COLUMN_FRIENDLY_NAME = "friendlyName";
	
    public static final String      COLUMN_CLASSIFICATION = "classification";
	
    public static final String      COLUMN_SCIENTIFIC_NAME = "scientificName";
	
    public static final String      COLUMN_DANGER_LEVEL = "dangerLevel";
    
    public static final String      COLUMN_IMAGE_ASSET = "imageAsset";

    //Used to read data from res/ and assets/
    private Resources mResources;

    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database
		//COMPLETED:created
        final String CREATE_TABLE = "CREATE TABLE "  + TABLE_NAME + " (" +
                COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_FRIENDLY_NAME + " TEXT NOT NULL, " +
                COLUMN_SCIENTIFIC_NAME + " TEXT NOT NULL, " +
                COLUMN_CLASSIFICATION + " TEXT NOT NULL, " +
                COLUMN_IMAGE_ASSET + " TEXT NOT NULL, " +
                COLUMN_DANGER_LEVEL+ " INT NOT NULL);";

        db.execSQL(CREATE_TABLE);
        Log.e(TAG, "Db created");

        //only isert the asset from insect.json if the db is being created for the first time
        try {
            readInsectsFromResources(db);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage()+" IOException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage()+" JSONException");

            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Query for altering table here
		final String ALTER_TABLE = "";
		
		if(ALTER_TABLE.equals("")){
			db.execSQL(ALTER_TABLE);
		}
		else
		{
			//no alter query so drop the existing database
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			//create a new database
			onCreate(db);
        
		}
		
        
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

//        db = BugsDbHelper.getWri

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

//        BugsDbHelper mBugsDbHelper = new BugsDbHelper()
//        final SQLiteDatabase db = BugsDbHelper.getReadableDatabase();

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance

        JSONObject jsonObject = new JSONObject(rawJson);

        JSONArray arr = jsonObject.getJSONArray("insects");


        for (int i = 0; i<arr.length(); i++){
//        for (int i = 0; i<jsonObject.length(); i++){
            JSONObject obj = arr.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            // Put the insect  into the ContentValues
            contentValues.put(COLUMN_FRIENDLY_NAME, obj.getString("friendlyName"));
            contentValues.put(COLUMN_SCIENTIFIC_NAME, obj.getString("scientificName"));
            contentValues.put(COLUMN_CLASSIFICATION, obj.getString("classification"));
            contentValues.put(COLUMN_IMAGE_ASSET, obj.getString("imageAsset"));
            contentValues.put(COLUMN_DANGER_LEVEL, obj.getInt("dangerLevel"));

            long id =db.insert(TABLE_NAME,null,contentValues);

            Log.e(TAG, "including data: "+id);

        }
    }
}
