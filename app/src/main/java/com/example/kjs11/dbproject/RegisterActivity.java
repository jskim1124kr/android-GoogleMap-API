package com.example.kjs11.dbproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by kjs11 on 2017-12-12.
 */

public class RegisterActivity extends Activity {

    EditText regi;
    Button go;
    String my_school;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        regi = findViewById(R.id.school_input);
        go =findViewById(R.id.goBtn);


        go.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick( View v ) {
                        if ( regi.getText().toString().length() != 0 ) {
                            my_school = regi.getText().toString();
                            Log.i("Jisu", "myschool in ResigterActivty.. : " + my_school);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("school", my_school);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

    }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}