package com.example.kjs11.dbproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by user on 2017-12-03.
 */

public class MainActivity extends Activity{
    ImageView first,second,third;
    private long pressedTime;
    String my_place;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        first =  (ImageView)findViewById(R.id.first_menu_image);
        second = (ImageView)findViewById(R.id.second_menu_image);
        third = (ImageView)findViewById(R.id.third_menu_image);
        first.setImageResource(R.drawable.map);
        second.setImageResource(R.drawable.sairen);
        third.setImageResource(R.drawable.shi);
        my_place = getIntent().getStringExtra("school");

    }




    public void firstBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.putExtra("school", my_place);
        startActivity(intent);


    }
    public void secondBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(),SirenActivity.class);
        startActivity(intent);
    }
    public void thirdBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(),GuideActivity1.class);
        startActivity(intent);
    }

    public void regiBtnClicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);

    }

    public void onBackPressed()
    {
        if(pressedTime == 0) {
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {

            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if(seconds > 2000) { pressedTime = 0; } else { finish(); }
        }

    }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
