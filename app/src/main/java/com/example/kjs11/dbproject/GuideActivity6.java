package com.example.kjs11.dbproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by kjs11 on 2017-12-08.
 */

public class GuideActivity6 extends Activity{
    ImageView image;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity6);
       image = (ImageView)findViewById(R.id.guide6);
       image.setImageResource(R.drawable.help6);


    }

    public void nextBtnClicked6(View v) {
        Intent intent = new Intent(getApplicationContext(), GuideActivity7.class);
        startActivity(intent);
        this.finish();
    }

    public void prevBtnClicked6(View v) {
        Intent intent = new Intent(getApplicationContext(), GuideActivity5.class);
        startActivity(intent);
        this.finish();
    }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
