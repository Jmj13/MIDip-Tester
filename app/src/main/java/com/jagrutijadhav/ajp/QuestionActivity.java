package com.jagrutijadhav.ajp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import thereisnospon.codeview.CodeView;


public class QuestionActivity extends AppCompatActivity {
    public TextView quetext,queno,secondtime,minittime;
    public RadioButton option1,option2,option3,option4;
    private int radioarray[]=new int[35];
    private String answsertext[]=new String[35];
    public CodeView codeView;
    RelativeLayout question_page;
    private String checktext[]=new String[35];
    private RadioGroup group;
    DatabaseAccess databaseAccess;
    private int second;
    private int minit=30;
    private int i=0;
    private String text;
    private int temp=1;

    Button nextsubmit;
    public int testlevel,chapter_no;
    public String subject;
    public  int speed=0;
   ImageView voiceDemo;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
       getSupportActionBar().hide();
        question_page=findViewById(R.id.question_page);
        codeView=(CodeView)findViewById(R.id.codeview);
        nextsubmit=findViewById(R.id.next_submit);
        group=findViewById(R.id.radiogroup);
        minittime=findViewById(R.id.minittime);
        voiceDemo=findViewById(R.id.volume);
        quetext=findViewById(R.id.question);
        secondtime=findViewById(R.id.secondtime);
        option1=findViewById(R.id.option1);
        option2=findViewById (R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        queno=findViewById(R.id.question_num);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int lang=textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        testlevel=getIntent().getIntExtra("testlevel",1);
        chapter_no=getIntent().getIntExtra("CHAPTER",1);
        subject=getIntent().getStringExtra("SUBJECT");
       // Log.e("testlevel",""+testlevel+"/nchapter="+chapter_no);
        nextsubmit.callOnClick();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               option1.setBackgroundResource(R.color.black);
               option2.setBackgroundResource(R.color.black);
               option3.setBackgroundResource(R.color.black);
               option4.setBackgroundResource(R.color.black);
                if(option1.isChecked())
                {
                   option1.setBackgroundResource(R.color.colorPrimary);
                    radioarray[i]=1;checktext[i]=option1.getText().toString();

                }
                else if(option2.isChecked())
                {
                    option2.setBackgroundResource(R.color.colorPrimary);
                    radioarray[i]=2;checktext[i]=option2.getText().toString();
                }

                else if(option3.isChecked()){
                    option3.setBackgroundResource(R.color.colorPrimary);
                    radioarray[i]=3;checktext[i]=option3.getText().toString();
                }
                else if(option4.isChecked())
                {
                    option4.setBackgroundResource(R.color.colorPrimary);
                    radioarray[i]=4;checktext[i]=option4.getText().toString();
                }
            }
        });
        timer();
    }
    private void timer()
    { final Handler h =new Handler();
        second=60;
        h.post(new Runnable() {
            @Override
            public void run() {

                if(second>0)
                {
                    secondtime.setText(""+second);
                    second--;
                    h.postDelayed(this,1000);
                }
                else{
                    minit--;
                    minittime.setText(minit+": ");
                    if(minit==0){result();}
                    timer();
                }
            }
        });
    }
    private void result()
    {   int score=calculatescore();
        Intent i1 = new Intent(this,Result_Activity.class);
        i1.putExtra("SCORE",score);
        i1.putExtra("CHAPTER",chapter_no);
        i1.putExtra("SUBJECT",subject+"");
        i1.putExtra("testlevel",testlevel);
        startActivity(i1);
        finish();
    }


    @Override
    protected void onPause() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    public void next_and_submit(View view){
        databaseAccess=DatabaseAccess.getInstance(getApplicationContext(),textToSpeech,quetext,codeView,queno,option1,option2,option3,option4);
        databaseAccess.open();
        i++;
        if(temp<i){temp=i;}


        text = databaseAccess.getAddress(subject,i,chapter_no,testlevel);
        answsertext[i]=text;
        databaseAccess.voicechek(speed);

        revesed();
        databaseAccess.close();
        if((nextsubmit.getText().toString())=="S"){
            int score=calculatescore();
            Intent i1 = new Intent(this,Result_Activity.class);
            i1.putExtra("SCORE",score);
            i1.putExtra("CHAPTER",chapter_no);
            i1.putExtra("SUBJECT",subject+"");
            i1.putExtra("testlevel",testlevel);
            startActivity(i1);
            finish();
        }
        if(i==30) {
            nextsubmit.setText("S");
        }
    }

    public void previous(View view) {
        if (i != 1) {

            nextsubmit.setText("N");
            databaseAccess = DatabaseAccess.getInstance(getApplicationContext(),textToSpeech,quetext, codeView, queno, option1, option2, option3, option4);
            databaseAccess.open();
            i--;
            text = databaseAccess.getAddress(subject,i,chapter_no,testlevel);
            databaseAccess.voicechek(speed);
            revesed();
            databaseAccess.close();
        }
    }


    @SuppressLint("ResourceAsColor")
    private void revesed() {
        switch (radioarray[i]){
            case 1:option1.setChecked(true);
            option1.setBackgroundResource(R.color.colorPrimary);
            break;
            case 2:option2.setChecked(true);
                option2.setBackgroundResource(R.color.colorPrimary);
                break;
            case 3:option3.setChecked(true);
                option3.setBackgroundResource(R.color.colorPrimary);
                break;
            case 4:option4.setChecked(true);
                option4.setBackgroundResource(R.color.colorPrimary);
                break;
            case 0:group.clearCheck();break;
        }
    }
    private int calculatescore() {
        int s=0;
        for(int j=1;j<=temp;j++){
            Log.e("QA", answsertext[j]+", "+checktext[j]);
            if(answsertext[j].equals(checktext[j])){
                s=s+1;
            }
        }
        return s;
    }
    public void examexit(View view) {
        int score=calculatescore();
        Intent i1 = new Intent(this,Result_Activity.class);
        i1.putExtra("SCORE",score);
        i1.putExtra("CHAPTER",chapter_no);
        i1.putExtra("SUBJECT",subject+"");
        i1.putExtra("testlevel",testlevel);
        startActivity(i1);
        finish();
    }

    @Override
    protected void onResume() {
        question_page.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    public void voice(View view) {
        databaseAccess=DatabaseAccess.getInstance(getApplicationContext(),textToSpeech,quetext,codeView,queno,option1,option2,option3,option4);
        if(speed==1){
            speed=0;
            textToSpeech.stop();
            voiceDemo.setImageResource(R.drawable.ic_volume_off_black_24dp);
            databaseAccess.voicechek(speed);

        }
        else{
            speed=1;
            voiceDemo.setImageResource(R.drawable.ic_volume_up_black_24dp);
            databaseAccess.voicechek(speed);
        }
    }
}
