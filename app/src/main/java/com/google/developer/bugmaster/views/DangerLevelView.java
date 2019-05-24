package com.google.developer.bugmaster.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.developer.bugmaster.R;

public class DangerLevelView extends android.support.v7.widget.AppCompatTextView {

    int mDangerLevel;
    static Context mContext;

    public DangerLevelView(Context context) {
        super(context);
        mContext = context;
    }

    public DangerLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void setDangerLevel(int dangerLevel) {
        //COMPLETED: Update the view appropriately based on the level input
        mDangerLevel = dangerLevel;
        Resources res = mContext.getResources();
        String[] dangerColors = res.getStringArray(R.array.dangerColors);
        setText(getDangerLevel()+"");
        Drawable d = ContextCompat.getDrawable(getContext(),R.drawable.background_danger);
        d.setColorFilter(Color.parseColor(dangerColors[dangerLevel-1]), PorterDuff.Mode.DARKEN);
        setBackground(d);
//        setBackgroundResource(R.drawable.background_danger);

//        setBackgroundColor(Color.parseColor(dangerColors[dangerLevel-1]));



    }


    public int getDangerLevel() {
        //TODO: Report the current level back as an integer
        return mDangerLevel;
    }
}
