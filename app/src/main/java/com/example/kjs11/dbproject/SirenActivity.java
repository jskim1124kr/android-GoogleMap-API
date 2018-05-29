package com.example.kjs11.dbproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by kjs11 on 2017-12-03.
 */


public class SirenActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private boolean isSirenOn;

    ImageView zito;
    TextView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.siren_activity);
        zito = (ImageView)findViewById(R.id.guardZito);
        zito.setImageResource(R.drawable.siren);
        state = findViewById(R.id.sirenState);

        isSirenOn = true;
        mediaPlayer = new MediaPlayer();

        try{
            AssetFileDescriptor descriptor = getAssets().openFd("PoliceSiren.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: " + ex.toString(), Toast.LENGTH_SHORT).show();
            mediaPlayer = null;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setVolume(1.0f,1.0f);
        }
        if (isSirenOn == false) {
            mediaPlayer.pause();
        } else
            mediaPlayer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if(mediaPlayer != null){
            mediaPlayer.pause();


            if(isFinishing()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }
    public void btnClicked(View v) {

        if (isSirenOn) {
            isSirenOn = false;
            zito.setImageResource(R.drawable.sirenoff);
            state.setText("지토에게 도움을 요청해줘!");

            onResume();

        } else {
            isSirenOn = true;
            zito.setImageResource(R.drawable.siren);
            state.setText("지토가 도움 요청하는중 !!");
            onResume();
        }
    }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}