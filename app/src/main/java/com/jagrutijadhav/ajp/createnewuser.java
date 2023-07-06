package com.jagrutijadhav.ajp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class createnewuser extends AppCompatActivity {
    //Variables
    Student student;
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    AlertDialog.Builder builder;
    Button regBtn, regToLoginBtn;
    Intent intent;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String subject_array[]={"AJP","EST","MAN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewuser);
        builder = new AlertDialog.Builder(this);
        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else if(val.length()==10) {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }else {
            regPhoneNo.setError("Number must be 10 digit+");
                return  false;
        }
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {

        if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
            return;
        }


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
//            toast=Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection....",Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER,0,0);
//            toast.show();
            AlertDialog.Builder a=new AlertDialog.Builder(createnewuser.this);
            a.setTitle("MIDip Tester");
            a.setMessage("Please Check Your Internet Connection");
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS  ) ));
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.show();
        }else{

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");
            final String name = regName.getEditText().getText().toString().trim();
            final String username = regUsername.getEditText().getText().toString().trim();
            final String email = regEmail.getEditText().getText().toString().trim();
            final String phoneNo = regPhoneNo.getEditText().getText().toString().trim();
            final String password = regPassword.getEditText().getText().toString();
            final String urlname="empty",ajprp="0",estrp="0",manrp="0";



            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(username).exists()){
                        regUsername.setError("UserName alredy exit...Please Enter Another UserName");
                    }
                    else{
//Toast.makeText(getApplicationContext(),"Lllllll",Toast.LENGTH_LONG).show();
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        final View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_input, viewGroup, false);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle("MIDip Tester");
                        builder.setMessage("This information is usefull when you forget password");
                        final EditText input_color = dialogView.findViewById(R.id.amount);
                        final EditText input_friend=dialogView.findViewById(R.id.user_id);
                        input_color.setInputType(InputType.TYPE_CLASS_TEXT);
                        input_friend.setInputType(InputType.TYPE_CLASS_TEXT);
                       // ( (TextView)dialogView.findViewById(R.id.t2)).setText("Color: ");
                       // ( (TextView)dialogView.findViewById(R.id.t1)).setText("Friend: ");
                       // input_color.setHint("Enter Favourite Color");
                       // input_friend.setHint("Enter Best Friend Name");
                        builder.setView(dialogView);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String color_=input_color.getText().toString().trim();
                                String friend=input_friend.getText().toString().trim();
                                if(color_.equals("") || friend.equals("")){
                                    Toast.makeText(getApplicationContext(),"Plz Enter Proper information",Toast.LENGTH_LONG).show();
                                }else{
                                    student = new Student(name, username, email, phoneNo, password, urlname,color_,friend,ajprp,estrp,manrp);
                                    reference.child(username).setValue(student);
                                    MyHelper helper=new MyHelper(getApplicationContext());
                                    SQLiteDatabase database=helper.getWritableDatabase();
                                    ContentValues values=new ContentValues();
                                    values.put("USER_NAME",username);
                                    values.put("PASSWORD",password);
                                    values.put("STATUS","true");
                                    database.update("USER",values,"id=?",new String[]{"1"});
                                   for(int i=0;i<subject_array.length;i++){
                                       for(int j=1;j<=6;j++){
                                           reference.child(username).child(subject_array[i]).child("chapter" + j).child("RP"+j).setValue("0.0");
                                           for(int k=1;k<=5;k++) {
                                               reference.child(username).child(subject_array[i]).child("chapter" + j).child("test"+k).setValue("0");
                                           }
                                       }
                                   }


                                    intent=new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

        }
    }

    public void loginuserpade(View view) {
        startActivity(new Intent(getApplicationContext(),loginpage.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),loginpage.class));
        finish();
          //  super.onBackPressed();

    }

}