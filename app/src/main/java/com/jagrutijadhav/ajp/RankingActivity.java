package com.jagrutijadhav.ajp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;

public class RankingActivity extends AppCompatActivity
{
    ConstraintLayout  ranking_layout;
    RecyclerView recview;
    String subject_text;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
         getSupportActionBar().hide();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(RankingActivity.this);
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


        subject_text=getIntent().getStringExtra("SUBJECT");
        recview=(RecyclerView)findViewById(R.id.recview);
      LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
// Set the layout manager to your recyclerview
        recview.setLayoutManager(mLayoutManager);
        ranking_layout=findViewById(R.id.ranking_page_layout);
        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild(subject_text.toLowerCase()+"rp"), Student.class)
                        .build();


        adapter=new myadapter(options);
        adapter.subjectset(subject_text);
        recview.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onResume() {
        ranking_layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

}