package com.jagrutijadhav.ajp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class UserProfile extends AppCompatActivity {
    CircularImageView userprofile;
Intent intent;
    String nameFromDB,phoneNoFromDB,usernameFromDB,emailFromDB,passwordDB;
    public static String urlnameDB;
    public static String username1;
    public int Pick_Image_Code=111;
    TextInputLayout fullName,email,phoneNo,password;
    TextView fullNameLabel,usernameLabel,ajpRP,estRP,manRP;
    Cursor c;
    ScrollView userprofilelayout;
    private Uri uriname;
    private StorageReference s_ref;
    private DatabaseReference d_ref;

    @Override
    protected void onResume() {
        userprofilelayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();
        userprofilelayout = findViewById(R.id.userprofilelayout);
        ////  balance_label=findViewById(R.id.balance_label);
        //withdrawRs=findViewById(R.id.withdraw_label);

        //win_label=findViewById(R.id.winning_label);
        //withdraw_status=findViewById(R.id.withdraw_status);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(UserProfile.this);
            a.setTitle(R.string.app_name);
            a.setMessage("Please Check Your Internet Connection");
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.show();
        }
        MyHelper helper = new MyHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();
        c = database.rawQuery("select * from USER", new String[]{});
        if (c != null) {
            c.moveToFirst();
        }
        do {
            username1 = c.getString(1);
        } while (c.moveToNext());
        s_ref = FirebaseStorage.getInstance().getReference();
        d_ref = FirebaseDatabase.getInstance().getReference("users").child(username1);
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);
        userprofile = (CircularImageView) findViewById(R.id.profile_image);
        estRP = findViewById(R.id.est_rank_number);
        ajpRP = findViewById(R.id.ajp_rank_number);
        manRP = findViewById(R.id.man_rank_number);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(username1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameFromDB = dataSnapshot.child("name").getValue(String.class);
                usernameFromDB = dataSnapshot.child("username").getValue(String.class);
                phoneNoFromDB = dataSnapshot.child("phoneNo").getValue(String.class);
                emailFromDB = dataSnapshot.child("email").getValue(String.class);
                passwordDB = dataSnapshot.child("password").getValue(String.class);
                urlnameDB = dataSnapshot.child("urlname").getValue(String.class);
                ajpRP.setText(dataSnapshot.child("ajprp").getValue(String.class));
                estRP.setText(dataSnapshot.child("estrp").getValue(String.class));
                manRP.setText(dataSnapshot.child("manrp").getValue(String.class));
                //    avl_balance=dataSnapshot.child("balance").getValue(String.class);
                //  win_balance=""+(Integer.parseInt(dataSnapshot.child("winningRs").getValue(String.class))+Integer.parseInt(dataSnapshot.child("win_gatka").getValue(String.class)));
                fullNameLabel.setText(nameFromDB);
                usernameLabel.setText(usernameFromDB);
                fullName.getEditText().setText(nameFromDB);
                email.getEditText().setText(emailFromDB);
                phoneNo.getEditText().setText("91 " + phoneNoFromDB);
                password.getEditText().setText(passwordDB);
//                balance_label.setText(avl_balance);
//                win_label.setText(win_balance);
//                withdraw_status.setText(dataSnapshot.child("status").getValue(String.class));
//                withdrawRs.setText(dataSnapshot.child("withdrawRs").getValue(String.class));
                if (!(urlnameDB.equals("empty"))) {
                    Picasso.with(getApplicationContext()).load(urlnameDB).into(userprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        userprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
//                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions,1010);
//                    }else{
//                        pickImage();
//                    }
//                }else{
//                    pickImage();
//                }
//            }
//        });
//
//    }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_Image_Code && resultCode == RESULT_OK) {
            uriname = data.getData();
            Picasso.with(this).load(data.getData()).into(userprofile);
            upLoadeIcone();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void upLoadeIcone() {
        final StorageReference fileRefreance = s_ref.child(username1 + "." + getFileExtension(uriname));
        fileRefreance.putFile(uriname)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRefreance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                d_ref.child("urlname").setValue(String.valueOf(uri));

                            }
                        });
                        Toast.makeText(getApplicationContext(), "successfully upload", Toast.LENGTH_SHORT).show();

                    }
                });
    }



//    public void setuserprofile(View view) {
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
//                String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
//                requestPermissions(permissions,1010);
//            }else{
//                pickImage();
//            }
//        }else{
//            pickImage();
//        }
//    }
//
//    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1010:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImage();
                }else{
                    Toast.makeText(getApplicationContext(),"Permission denied....!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void pickImage() {

        Intent g=new Intent(Intent.ACTION_PICK);
        g.setType("image/*");
        startActivityForResult(g,Pick_Image_Code);
    }


    public void userperformance(View view) {
      intent=new Intent(getApplicationContext(),UserPerformance.class);
            String[] singleChoiceItems = {"AJP","EST","MAN"};
            final int[] itemSelected = {0};
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Select Subject")
                    .setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                            itemSelected[0] = selectedIndex;
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (itemSelected[0]) {
                                case 0:
                                   intent.putExtra("SUBJECT","AJP");
                                   startActivity(intent);
                                    break;
                                case 1:
                                    intent.putExtra("SUBJECT","EST");
                                    startActivity(intent);;
                                    break;
                                case 2:
                                    intent.putExtra("SUBJECT","MAN");
                                    startActivity(intent);
                                    break;
                            }
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
    }

    public void setUserProfileInProfilePage(View view) {

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ImageView circularImageView=(ImageView) dialoglayout.findViewById(R.id.dialog_img1);
        // circularImageView.setImageResource(R.drawable.background);
        circularImageView.setImageDrawable(userprofile.getDrawable());
        builder.setView(dialoglayout);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("MIDip Tester");
        builder.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,1010);
                    }else{
                        pickImage();
                    }
                }else{
                    pickImage();
                }

                //  startActivity(new Intent(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS  ) ));
            }
        }).setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StorageReference img = FirebaseStorage.getInstance().getReferenceFromUrl("gs://databasedemo-97731.appspot.com/" + username1 + ".jpg");
                img.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        d_ref.child("urlname").setValue("empty");
                        userprofile.setImageResource(R.drawable.profile1);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Already Deleted....", Toast.LENGTH_LONG).show();
                    }
                });
                //  startActivity(new Intent(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS  ) ));
            }
        });
        builder.show();


    }
}




