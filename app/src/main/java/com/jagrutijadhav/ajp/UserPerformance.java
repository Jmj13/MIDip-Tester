package com.jagrutijadhav.ajp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPerformance extends AppCompatActivity implements ValueEventListener {
TableRow tableRow6;
DatabaseReference ref;
String subject_text;
TextView subject_text_textview;
TextView capter1_test1,capter1_test2,capter1_test3,capter1_test4,capter1_test5,capter1_total,capter1_rp;
TextView capter2_test1,capter2_test2,capter2_test3,capter2_test4,capter2_test5,capter2_total,capter2_rp;
TextView capter3_test1,capter3_test2,capter3_test3,capter3_test4,capter3_test5,capter3_total,capter3_rp;
TextView capter4_test1,capter4_test2,capter4_test3,capter4_test4,capter4_test5,capter4_total,capter4_rp;
TextView capter5_test1,capter5_test2,capter5_test3,capter5_test4,capter5_test5,capter5_total,capter5_rp;
TextView capter6_test1,capter6_test2,capter6_test3,capter6_test4,capter6_test5,capter6_total,capter6_rp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_performance);
        getSupportActionBar().hide();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(UserPerformance.this);
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
        tableRow6=findViewById(R.id.row6);
        subject_text_textview=findViewById(R.id.per_subject);
        subject_text_textview.setText(subject_text);
        //chapter1
        capter1_test1=findViewById(R.id.chapter1_test1);
        capter1_test2=findViewById(R.id.chapter1_test2);
        capter1_test3=findViewById(R.id.chapter1_test3);
        capter1_test4=findViewById(R.id.chapter1_test4);
        capter1_test5=findViewById(R.id.chapter1_test5);
        capter1_total=findViewById(R.id.chapter1_total);
        capter1_rp=findViewById(R.id.chapter1_rp);
        //capter2
        capter2_test1=findViewById(R.id.chapter2_test1);
        capter2_test2=findViewById(R.id.chapter2_test2);
        capter2_test3=findViewById(R.id.chapter2_test3);
        capter2_test4=findViewById(R.id.chapter2_test4);
        capter2_test5=findViewById(R.id.chapter2_test5);
        capter2_total=findViewById(R.id.chapter2_total);
        capter2_rp=findViewById(R.id.chapter2_rp);
        //chapter3
        capter3_test1=findViewById(R.id.chapter3_test1);
        capter3_test2=findViewById(R.id.chapter3_test2);
        capter3_test3=findViewById(R.id.chapter3_test3);
        capter3_test4=findViewById(R.id.chapter3_test4);
        capter3_test5=findViewById(R.id.chapter3_test5);
        capter3_total=findViewById(R.id.chapter3_total);
        capter3_rp=findViewById(R.id.chapter3_rp);
        //chapter4
        capter4_test1=findViewById(R.id.chapter4_test1);
        capter4_test2=findViewById(R.id.chapter4_test2);
        capter4_test3=findViewById(R.id.chapter4_test3);
        capter4_test4=findViewById(R.id.chapter4_test4);
        capter4_test5=findViewById(R.id.chapter4_test5);
        capter4_total=findViewById(R.id.chapter4_total);
        capter4_rp=findViewById(R.id.chapter4_rp);
        //chapter5
        capter5_test1=findViewById(R.id.chapter5_test1);
        capter5_test2=findViewById(R.id.chapter5_test2);
        capter5_test3=findViewById(R.id.chapter5_test3);
        capter5_test4=findViewById(R.id.chapter5_test4);
        capter5_test5=findViewById(R.id.chapter5_test5);
        capter5_total=findViewById(R.id.chapter5_total);
        capter5_rp=findViewById(R.id.chapter5_rp);
        //chapter6
        capter6_test1=findViewById(R.id.chapter6_test1);
        capter6_test2=findViewById(R.id.chapter6_test2);
        capter6_test3=findViewById(R.id.chapter6_test3);
        capter6_test4=findViewById(R.id.chapter6_test4);
        capter6_test5=findViewById(R.id.chapter6_test5);
        capter6_total=findViewById(R.id.chapter6_total);
        capter6_rp=findViewById(R.id.chapter6_rp);
        if(subject_text.equals("EST") || subject_text.equals("MAN")){
            tableRow6.setVisibility(View.INVISIBLE);
        }

        //database ref
        ref=FirebaseDatabase.getInstance().getReference("users").child(HomePage.username).child(subject_text);
        ref.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        capter1_test1.setText(snapshot.child("chapter1").child("test1").getValue(String.class));
        capter1_test2.setText(snapshot.child("chapter1").child("test2").getValue(String.class));
        capter1_test3.setText(snapshot.child("chapter1").child("test3").getValue(String.class));
        capter1_test4.setText(snapshot.child("chapter1").child("test4").getValue(String.class));
        capter1_test5.setText(snapshot.child("chapter1").child("test5").getValue(String.class));
        capter1_total.setText(""+(Integer.parseInt(snapshot.child("chapter1").child("test1").getValue(String.class))+
                              Integer.parseInt(snapshot.child("chapter1").child("test2").getValue(String.class)) +
                              Integer.parseInt(snapshot.child("chapter1").child("test3").getValue(String.class))+
                              Integer.parseInt(snapshot.child("chapter1").child("test4").getValue(String.class)) +
                              Integer.parseInt(snapshot.child("chapter1").child("test5").getValue(String.class))));
        capter1_rp.setText(snapshot.child("chapter1").child("RP1").getValue(String.class));
        //chapter2
        capter2_test1.setText(snapshot.child("chapter2").child("test1").getValue(String.class));
        capter2_test2.setText(snapshot.child("chapter2").child("test2").getValue(String.class));
        capter2_test3.setText(snapshot.child("chapter2").child("test3").getValue(String.class));
        capter2_test4.setText(snapshot.child("chapter2").child("test4").getValue(String.class));
        capter2_test5.setText(snapshot.child("chapter2").child("test5").getValue(String.class));
        capter2_total.setText(""+(Integer.parseInt(snapshot.child("chapter2").child("test1").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter2").child("test2").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter2").child("test3").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter2").child("test4").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter2").child("test5").getValue(String.class))));
        capter2_rp.setText(snapshot.child("chapter2").child("RP2").getValue(String.class));
        //chapter3
        capter3_test1.setText(snapshot.child("chapter3").child("test1").getValue(String.class));
        capter3_test2.setText(snapshot.child("chapter3").child("test2").getValue(String.class));
        capter3_test3.setText(snapshot.child("chapter3").child("test3").getValue(String.class));
        capter3_test4.setText(snapshot.child("chapter3").child("test4").getValue(String.class));
        capter3_test5.setText(snapshot.child("chapter3").child("test5").getValue(String.class));
        capter3_total.setText(""+(Integer.parseInt(snapshot.child("chapter3").child("test1").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter3").child("test2").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter3").child("test3").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter3").child("test4").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter3").child("test5").getValue(String.class))));
        capter3_rp.setText(snapshot.child("chapter3").child("RP3").getValue(String.class));
        //chapter4
        capter4_test1.setText(snapshot.child("chapter4").child("test1").getValue(String.class));
        capter4_test2.setText(snapshot.child("chapter4").child("test2").getValue(String.class));
        capter4_test3.setText(snapshot.child("chapter4").child("test3").getValue(String.class));
        capter4_test4.setText(snapshot.child("chapter4").child("test4").getValue(String.class));
        capter4_test5.setText(snapshot.child("chapter4").child("test5").getValue(String.class));
        capter4_total.setText(""+(Integer.parseInt(snapshot.child("chapter4").child("test1").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter4").child("test2").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter4").child("test3").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter4").child("test4").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter4").child("test5").getValue(String.class))));
        capter4_rp.setText(snapshot.child("chapter4").child("RP4").getValue(String.class));
        //chapter5
        capter5_test1.setText(snapshot.child("chapter5").child("test1").getValue(String.class));
        capter5_test2.setText(snapshot.child("chapter5").child("test2").getValue(String.class));
        capter5_test3.setText(snapshot.child("chapter5").child("test3").getValue(String.class));
        capter5_test4.setText(snapshot.child("chapter5").child("test4").getValue(String.class));
        capter5_test5.setText(snapshot.child("chapter5").child("test5").getValue(String.class));
        capter5_total.setText(""+(Integer.parseInt(snapshot.child("chapter5").child("test1").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter5").child("test2").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter5").child("test3").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter5").child("test4").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter5").child("test5").getValue(String.class))));
        capter5_rp.setText(snapshot.child("chapter5").child("RP5").getValue(String.class));
        //chapter6
        capter6_test1.setText(snapshot.child("chapter6").child("test1").getValue(String.class));
        capter6_test2.setText(snapshot.child("chapter6").child("test2").getValue(String.class));
        capter6_test3.setText(snapshot.child("chapter6").child("test3").getValue(String.class));
        capter6_test4.setText(snapshot.child("chapter6").child("test4").getValue(String.class));
        capter6_test5.setText(snapshot.child("chapter6").child("test5").getValue(String.class));
        capter6_total.setText(""+(Integer.parseInt(snapshot.child("chapter6").child("test1").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter6").child("test2").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter6").child("test3").getValue(String.class))+
                Integer.parseInt(snapshot.child("chapter6").child("test4").getValue(String.class)) +
                Integer.parseInt(snapshot.child("chapter6").child("test5").getValue(String.class))));
        capter6_rp.setText(snapshot.child("chapter6").child("RP6").getValue(String.class));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}