package com.google.developer.bugmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.developer.bugmaster.data.Insect;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class InsectDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_INSECT = "insect_extra";
    ImageView imgView_image_asset;
    TextView tv_name, tv_scientific_name, tv_classification;
    RatingBar rb_danger_level;

    static Insect insect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolbar);
//        setSupportActionBar(toolbar);

        imgView_image_asset = (ImageView)findViewById(R.id.iv_image_asset);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_classification = (TextView) findViewById(R.id.tv_classification);
        tv_scientific_name = (TextView) findViewById(R.id.tv_scientific_name);
        rb_danger_level = (RatingBar)findViewById(R.id.rb_danger_level);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_INSECT)){
            insect = getIntent().getExtras().getParcelable(EXTRA_INSECT);
            setView(insect);
        }else {
            if(savedInstanceState != null){
                insect = savedInstanceState.getParcelable(EXTRA_INSECT);
                setView(insect);

            }
        }




        }

    private void setView(Insect insect) {
        tv_name.setText(insect.getName());
        tv_scientific_name.setText(insect.getScientificName());
        tv_classification.setText(insect.getClassification());
        String[] resNameArr = insect.getImageAsset().split("\\.");
        String resName = resNameArr[0];
        imgView_image_asset.setImageResource(getBaseContext().getResources().getIdentifier(resName, "drawable", getBaseContext().getPackageName()));
        rb_danger_level.setRating(insect.getDangerLevel());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_INSECT, insect);
    }


}
