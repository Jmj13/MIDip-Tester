package com.jagrutijadhav.ajp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class Chapter extends AppCompatActivity {
ScrollView scrollView;
String subject;
Intent intent;
Button chapter1,chapter2,chapter3,chapter4,chapter5,chapter6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chapter);
        scrollView=findViewById(R.id.chapter_layout);
        subject=getIntent().getStringExtra("SUBJECT");
        chapter1=findViewById(R.id.chapter1);
        chapter2=findViewById(R.id.chapter2);
        chapter3=findViewById(R.id.chapter3);
        chapter4=findViewById(R.id.chapter4);
        chapter5=findViewById(R.id.chapter5);
        chapter6=findViewById(R.id.chapter6);
        if(subject.equals("AJP")){
            chapter1.setText("Abstract Windowing Toolkit(AWT)");
            chapter2.setText("Swings");
            chapter3.setText("Event Handling)");
            chapter4.setText("Networking Basics");
            chapter5.setText("Interaction With Database");
            chapter6.setText("Servlets");
        }else if (subject.equals("EST")){
            chapter1.setText("Environment");
            chapter2.setText("Energy Resources");
            chapter3.setText("Ecosystem And Biodiversity)");
            chapter4.setText("Environmental Pollution");
            chapter5.setText("Social Issues And Environmental Education");
            chapter6.setVisibility(View.INVISIBLE);
        } else {
            chapter1.setText("Introduction To Management Concept And Management Skill");
            chapter2.setText("Planning And Organizing At Supervisory Level");
            chapter3.setText("Direction And Controlling At Supervisory Level)");
            chapter4.setText("Safety Management");
            chapter5.setText("Legislative Act");
            chapter6.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onResume() {
        scrollView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    public void click_chapter1(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",1);
        startActivity(intent);
    }

    public void click_chapter2(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",2);
        startActivity(intent);
    }

    public void click_chapter3(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",3);
        startActivity(intent);
    }

    public void click_chapter4(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",4);
        startActivity(intent);
    }

    public void click_chapter5(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",5);
        startActivity(intent);
    }

    public void click_chapter6(View view) {
        intent=new Intent(Chapter.this,Test_Level.class);
        intent.putExtra("SUBJECT",subject);
        intent.putExtra("CHAPTER",6);
        startActivity(intent);
    }
}