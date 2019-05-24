package com.google.developer.bugmaster.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.google.developer.bugmaster.data.BugsDbHelper.*;

public final class Insect implements Parcelable {
    private static final String TAG = Insect.class.getSimpleName();

    //Common name
    public final String name;
    //Latin scientific name
    public final String scientificName;
    //Classification order
    public final String classification;
    //Path to image resource
    public final String imageAsset;
    //1-10 scale danger to humans
    public final int dangerLevel;

    /**
     * Create a new Insect from discrete values
     */
    public Insect(String name, String scientificName, String classification, String imageAsset, int dangerLevel) {
        this.name = name;
        this.scientificName = scientificName;
        this.classification = classification;
        this.imageAsset = imageAsset;
        this.dangerLevel = dangerLevel;
    }

    /**
     * Create a new Insect from a database Cursor
     */
    public Insect(Cursor mCursor) {
        //TODO: Create a new insect from cursor
		//COMPLETED
		
		// Indices for the _id, scientificName, friendlyName, classification and dangerLevel columns
        //int idIndex = mCursor.getColumnIndex(BugsDBHelper.COLUMN_ID);
        int scientificNameIndex = mCursor.getColumnIndex(COLUMN_SCIENTIFIC_NAME);
        int nameIndex = mCursor.getColumnIndex(COLUMN_FRIENDLY_NAME);
        int classificationIndex = mCursor.getColumnIndex(COLUMN_CLASSIFICATION);
        int dangerLevelIndex = mCursor.getColumnIndex(COLUMN_DANGER_LEVEL);
		int imageAssetIndex = mCursor.getColumnIndex(COLUMN_IMAGE_ASSET);




        
        // Determine the values of the  data
        //final int id = mCursor.getInt(idIndex);
        String scientificName = mCursor.getString(scientificNameIndex);
        String name = mCursor.getString(nameIndex);
        String imageAsset = mCursor.getString(imageAssetIndex);
        String classification = mCursor.getString(classificationIndex);
        int dangerLevel = mCursor.getInt(dangerLevelIndex);

        
        this.name = name;
        this.scientificName = scientificName;
        this.classification = classification;
        this.imageAsset = imageAsset;
        this.dangerLevel = dangerLevel;
    }

    /**
     * Create a new Insect from a data Parcel
     */
    protected Insect(Parcel in) {
        this.name = in.readString();
        this.scientificName = in.readString();
        this.classification = in.readString();
        this.imageAsset = in.readString();
        this.dangerLevel = in.readInt();
    }

    public static String getTAG() {
        return TAG;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getClassification() {
        return classification;
    }

    public String getImageAsset() {
        return imageAsset;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public static Creator<Insect> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(scientificName);
        dest.writeString(classification);
        dest.writeString(imageAsset);
        dest.writeInt(dangerLevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Insect> CREATOR = new Creator<Insect>() {
        @Override
        public Insect createFromParcel(Parcel in) {
            return new Insect(in);
        }

        @Override
        public Insect[] newArray(int size) {
            return new Insect[size];
        }
    };
}
