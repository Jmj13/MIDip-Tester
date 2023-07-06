package com.jagrutijadhav.ajp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class Test_Level extends AppCompatActivity{
    ScrollView test_level_page;
    String subject;
    int chapter_no;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test__level);
        test_level_page=findViewById(R.id.test_level);
        subject=getIntent().getStringExtra("SUBJECT");
        chapter_no=getIntent().getIntExtra("CHAPTER",1);
    }
    @Override
    protected void onResume() {
        test_level_page.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    public void click_test_level1(View view) {
        intent = new Intent(Test_Level.this,QuestionActivity.class);
        intent.putExtra("SUBJECT",subject+"");
        intent.putExtra("CHAPTER",chapter_no);
        intent.putExtra("testlevel",1);
        startActivity(intent);
    }
    public void click_test_level2(View view) {
        intent = new Intent(Test_Level.this,QuestionActivity.class);
        intent.putExtra("SUBJECT",subject+"");
        intent.putExtra("CHAPTER",chapter_no);
        intent.putExtra("testlevel",2);
        startActivity(intent);
    }
    public void click_test_level3(View view) {
        intent = new Intent(Test_Level.this,QuestionActivity.class);
        intent.putExtra("SUBJECT",subject+"");
        intent.putExtra("CHAPTER",chapter_no);
        intent.putExtra("testlevel",3);
        startActivity(intent);
    }
    public void click_test_level4(View view) {
        intent = new Intent(Test_Level.this,QuestionActivity.class);
        intent.putExtra("SUBJECT",subject+"");
        intent.putExtra("CHAPTER",chapter_no);
        intent.putExtra("testlevel",4);
        startActivity(intent);
    }
    public void click_test_level5(View view) {
        intent = new Intent(Test_Level.this,QuestionActivity.class);
        intent.putExtra("SUBJECT",subject+"");
        intent.putExtra("CHAPTER",chapter_no);
        intent.putExtra("testlevel",5);
        startActivity(intent);
    }




}