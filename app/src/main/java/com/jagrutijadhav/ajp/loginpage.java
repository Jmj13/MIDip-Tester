package com.jagrutijadhav.ajp;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginpage extends AppCompatActivity {
    ViewGroup viewGroup;

    AlertDialog.Builder builder;
    DatabaseReference reference;
    Button callSignUp, login_btn;
    ImageView image;
    public static String username_text;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //This Line will hide the status bar from the screen
        reference=FirebaseDatabase.getInstance().getReference("users");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewGroup = findViewById(android.R.id.content);
        builder = new AlertDialog.Builder(this);
        MyHelper helper = new MyHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor c = database.rawQuery("select * from USER", new String[]{});
        if (c != null) {
            c.moveToFirst();
        }
        String status;
        do {
            status = c.getString(3);
            username_text=c.getString(1);
        } while (c.moveToNext());
        if (status.equals("true")) {
            Intent i = new Intent(getApplicationContext(), HomePage.class);
            startActivity(i);
            finish();
        } else {

            setContentView(R.layout.activity_loginpage);
            //Hooks
            callSignUp = findViewById(R.id.sign_screen);
            image = findViewById(R.id.logo_image);
            logoText = findViewById(R.id.logo_name);
            sloganText = findViewById(R.id.slogan_name);
            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            login_btn = findViewById(R.id.login_btn);
        }
    }
    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        else {
            isUser();
        }
    }

    public void isUser() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
//            toast=Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection....",Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER,0,0);
//            toast.show();
            AlertDialog.Builder a = new AlertDialog.Builder(loginpage.this);
            a.setTitle(R.string.app_name);
            a.setMessage("Please Check Your Internet Connection");
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS  ));
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.show();
        }else {
            final String userEnteredUsername = username.getEditText().getText().toString().trim();
            final String userEnteredPassword = password.getEditText().getText().toString().trim();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                        if (passwordFromDB.equals(userEnteredPassword)) {
                            username.setError(null);
                            username.setErrorEnabled(false);
                            MyHelper helper=new MyHelper(getApplicationContext());
                            SQLiteDatabase database=helper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("USER_NAME",userEnteredUsername);
                            values.put("PASSWORD",userEnteredPassword);
                            values.put("STATUS","true");
                            database.update("USER",values,"id=?",new String[]{"1"});

//
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            password.setError("Wrong Password");
                            password.requestFocus();
                        }
                    } else {
                        username.setError("No such User exist");
                        username.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(loginpage.this, createnewuser.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_text");
        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
        pairs[3] = new Pair<View, String>(username, "username_tran");
        pairs[4] = new Pair<View, String>(password, "password_tran");
        pairs[5] = new Pair<View, String>(login_btn, "button_tran");
        pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(loginpage.this, pairs);
            startActivity(intent, options.toBundle());
            finish();

        }
    }

    public void ForgatePassword(View view) {
        final   EditText forget_username,forget_color,forget_friend;


        final View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.forget_password_custom_layout, viewGroup, false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("MIDip Tester");
        forget_username = dialogView.findViewById(R.id.forget_username);
        forget_color = dialogView.findViewById(R.id.forget_color);
        forget_friend = dialogView.findViewById(R.id.forget_friend);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final   String color_=forget_color.getText().toString().trim();
                final    String friend=forget_friend.getText().toString().trim();
                final  String user=forget_username.getText().toString().trim();
                if(color_.equals("") || friend.equals("") || user.equals("")){
                    Toast.makeText(getApplicationContext(),"Plz Enter Proper information",Toast.LENGTH_LONG).show();
                }else{

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(user).exists()){
                                if(dataSnapshot.child(user).child("color_").getValue(String.class).trim().equalsIgnoreCase(color_) && dataSnapshot.child(user).child("friend").getValue(String.class).trim().equalsIgnoreCase(friend)){
                                    AlertDialog.Builder a = new AlertDialog.Builder(loginpage.this);
                                    a.setTitle(R.string.app_name);
                                    a.setMessage("Congratulation\nYour Password: "+dataSnapshot.child(user).child("password").getValue(String.class));
                                    a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    a.setIcon(R.mipmap.ic_launcher);
                                    a.show();
                                } else{
                                    Toast.makeText(getApplicationContext(),"Sorry,Wrong Info.",Toast.LENGTH_LONG).show();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"UserName Not Exists",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }});

                }
            }
        });
        builder.show();

    }
}
