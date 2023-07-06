package com.jagrutijadhav.ajp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Aboutus extends AppCompatActivity {
LinearLayout aboutus_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_aboutus);
        aboutus_layout=findViewById(R.id.aboutus_lyout);
    }
    @Override
    protected void onResume() {
        aboutus_layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

}
