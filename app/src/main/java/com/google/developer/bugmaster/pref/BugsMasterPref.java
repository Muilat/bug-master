package com.google.developer.bugmaster.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.google.developer.bugmaster.data.BugsDbHelper;

public class BugsMasterPref {
    public static final String KEY_SORT = "sort";
    public static final String KEY_NAME_SORT = BugsDbHelper.COLUMN_FRIENDLY_NAME;
    public static final String KEY_DANGER_SORT = BugsDbHelper.COLUMN_DANGER_LEVEL;

    private static final String DEFAULT_SORT = "name";

    synchronized public static void setSort(Context context, String sortBy) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SORT, sortBy);
        editor.apply();
    }

    public static String getSort(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortBy = prefs.getString(KEY_SORT, DEFAULT_SORT);
        return sortBy;

    }


}
