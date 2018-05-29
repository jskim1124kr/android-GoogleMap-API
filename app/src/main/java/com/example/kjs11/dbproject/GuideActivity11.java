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
 * Created by kjs11 on 2017-12-08.
 */

public class GuideActivity11 extends Activity{
    ImageView image;
    private long pressedTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity11);
       image = (ImageView)findViewById(R.id.guide11);
       image.setImageResource(R.drawable.help11);
    }

    public void nextBtnClicked11(View v) {
        Toast.makeText(this, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
    }

    public void prevBtnClicked11(View v) {
        Intent intent = new Intent(getApplicationContext(), GuideActivity10.class);
        startActivity(intent);
        this.finish();
    }
    public void onBackPressed()
    {
        if(pressedTime == 0) {
            Toast.makeText(this, "한번 더 누르면 메인화면으로 돌아갑니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {

            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if(seconds > 2000) { pressedTime = 0; } else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }
        }
    }

    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
