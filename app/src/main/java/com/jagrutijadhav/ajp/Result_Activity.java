package com.jagrutijadhav.ajp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class Result_Activity extends AppCompatActivity implements ValueEventListener {
ScrollView result_page;
String subject;
float rp;
int chapter,totolscore;
int test_level,score;
ImageView result_status;
    DatabaseReference reference;
    KonfettiView viewKonfetti;
CircularImageView result_user_profile;
TextView result_user_name,result_score,result_total_score,result_rp,subjectChapterTestlevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_);
        getSupportActionBar().hide();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(Result_Activity.this);
            a.setTitle(R.string.app_name);
            a.setMessage("Please Check Your Internet Connection");
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.setCancelable(true);
            a.show();
        }


        reference= FirebaseDatabase.getInstance().getReference("users").child(HomePage.username);
        result_user_profile=(CircularImageView)findViewById(R.id.result_userprofile);
        result_user_name=findViewById(R.id.result_userfullname);
        result_status=findViewById(R.id.result_status);
        subjectChapterTestlevel=findViewById(R.id.result_subject_chapter);
        result_score=findViewById(R.id.result_score);
        result_total_score=findViewById(R.id.result_total_score);
        result_rp=findViewById(R.id.result_RP);
        result_page=findViewById(R.id.result_activity);
        subject=getIntent().getStringExtra("SUBJECT");
        test_level=getIntent().getIntExtra("testlevel",1);
        chapter=getIntent().getIntExtra("CHAPTER",1);
        score=getIntent().getIntExtra("SCORE",0);
       if(!(HomePage.url_name.equals("empty"))){ Picasso.with(getApplicationContext()).load(HomePage.url_name).into(result_user_profile);}
        result_user_name.setText(HomePage.username);
        subjectChapterTestlevel.setText(subject+"-Chapter"+chapter+"-TestLevel"+test_level);
        viewKonfetti=findViewById(R.id.viewKonfetti);

        if(score>=10){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);
mediaPlayer.start();
            viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.RED)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5))
                    .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 5000L);

            result_score.setText("SCORE: "+score);
            result_status.setImageResource(R.drawable.passed);
            reference.child(subject).child("chapter"+chapter).child("test"+test_level).setValue(score+"");
            reference.addValueEventListener(this);
        }else{
            result_score.setText("SCORE: "+score);
            result_status.setImageResource(R.drawable.failed);
            reference.addValueEventListener(this);
        }
    }
    @Override
    protected void onResume() {
        result_page.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        totolscore= Integer.parseInt(dataSnapshot.child(subject).child("chapter"+chapter).child("test1").getValue(String.class))+
                Integer.parseInt(dataSnapshot.child(subject).child("chapter"+chapter).child("test2").getValue(String.class))+
                Integer.parseInt(dataSnapshot.child(subject).child("chapter"+chapter).child("test3").getValue(String.class))+
                Integer.parseInt(dataSnapshot.child(subject).child("chapter"+chapter).child("test4").getValue(String.class))+
                Integer.parseInt(dataSnapshot.child(subject).child("chapter"+chapter).child("test5").getValue(String.class));
               result_total_score.setText("TOTAL SCORE: "+totolscore);
               rp= (float) (totolscore/15.0*3.0);
               result_rp.setText("RP: "+rp);
               reference.child(subject).child("chapter"+chapter).child("RP"+chapter).setValue(rp+"");
               float totol_rp=Float.parseFloat(dataSnapshot.child(subject).child("chapter1").child("RP"+1).getValue(String.class))+
                       Float.parseFloat(dataSnapshot.child(subject).child("chapter2").child("RP"+2).getValue(String.class))+
                       Float.parseFloat(dataSnapshot.child(subject).child("chapter3").child("RP"+3).getValue(String.class))+
                       Float.parseFloat(dataSnapshot.child(subject).child("chapter4").child("RP"+4).getValue(String.class))+
                       Float.parseFloat(dataSnapshot.child(subject).child("chapter5").child("RP"+5).getValue(String.class))+
                        Float.parseFloat(dataSnapshot.child(subject).child("chapter6").child("RP"+6).getValue(String.class));
                      reference.child(subject.toLowerCase().trim()+"rp").setValue(totol_rp+"");
            }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) { }
}