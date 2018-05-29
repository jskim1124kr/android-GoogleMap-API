package com.example.kjs11.dbproject;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by kjs11 on 2017-12-11.
 */

public class Myfont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"fonts/myfont.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/myfont.ttf"));
    }
}
