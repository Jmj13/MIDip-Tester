package com.jagrutijadhav.ajp;
import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener {
DrawerLayout drawerLayout;
Toolbar toolbar;
    Uri uriname;
    private StorageReference s_ref;
    public int Pick_Image_Code=111;
public static String username="";
public static String url_name="empty",userFullname;
Intent intent;
DatabaseReference reference;
NavigationView navigationView;
    CircularImageView heder_userprofile;
    TextView menu_userName,menu_userFullName;
    View viewActionBar;
    @Override
    protected void onResume() {
        drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(HomePage.this);
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



        set_username();





        drawerLayout=findViewById(R.id.draw_layout);
        toolbar= findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        viewActionBar = navigationView.getHeaderView(0);
        heder_userprofile=(CircularImageView) viewActionBar.findViewById(R.id.menu_userprofile);
        s_ref= FirebaseStorage.getInstance().getReference();
         reference= FirebaseDatabase.getInstance().getReference("users").child(username);
        reference.addValueEventListener(this);
        heder_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile_Click();
            }
        });
        menu_userName=(TextView)viewActionBar.findViewById(R.id.menu_username);
        menu_userName.setText(username);
        menu_userFullName=viewActionBar.findViewById(R.id.menu_userfullname);//menu_userFullName.setText(userFullname);
        setSupportActionBar(toolbar);
       navigationView.bringToFront();
        ActionBarDrawerToggle  toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_Drawebal,R.string.CLOSE_DRAWEBLE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
       navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
       }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(HomePage.this,Aboutus.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_user_Profile:
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.rankmenuajp:
                intent=new Intent(getApplicationContext(),RankingActivity.class);
                intent.putExtra("SUBJECT","AJP");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.rankmenuest:
                intent=new Intent(getApplicationContext(),RankingActivity.class);
                intent.putExtra("SUBJECT","EST");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.rankmenuman:
                intent=new Intent(getApplicationContext(),RankingActivity.class);
                intent.putExtra("SUBJECT","MAN");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
                case R.id.menu_logout:
                MyHelper helper=new MyHelper(getApplicationContext());
                SQLiteDatabase database=helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("USER_NAME","null");
                values.put("PASSWORD","null");
                values.put("STATUS","false");
                database.update("USER",values,"id=?",new String[]{"1"});
                Intent i = new Intent(getApplicationContext(), loginpage.class);
                startActivity(i);
                finish();
                break;


        }

        return true;
    }


    public void clickOnAJP(View view) {
        intent=new Intent(getApplicationContext(),Chapter.class);
        intent.putExtra("SUBJECT","AJP");
        startActivity(intent);
    }

    public void clickOnEST(View view) {
        intent=new Intent(getApplicationContext(),Chapter.class);
        intent.putExtra("SUBJECT","EST");
        startActivity(intent);
    }

    public void clickOnMAN(View view) {
        intent=new Intent(getApplicationContext(),Chapter.class);
        intent.putExtra("SUBJECT","MAN");
        startActivity(intent);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        url_name=dataSnapshot.child("urlname").getValue(String.class);
     if(!((url_name+"").equals("empty"))){ Picasso.with(getApplicationContext()).load(url_name).into(heder_userprofile);}
        //Log.e("url=",""+url_name);

        userFullname=dataSnapshot.child("name").getValue(String.class);
        menu_userFullName.setText(userFullname);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
    public void set_username(){
        MyHelper helper = new MyHelper(getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor c = database.rawQuery("select * from USER", new String[]{});
        if (c != null) { c.moveToFirst(); }
        do {
            username=c.getString(1);
        } while (c.moveToNext());
    }

    public void  userProfile_Click(){
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ImageView circularImageView=(ImageView) dialoglayout.findViewById(R.id.dialog_img1);
       // circularImageView.setImageResource(R.drawable.background);
        circularImageView.setImageDrawable(heder_userprofile.getDrawable());
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
                StorageReference img = FirebaseStorage.getInstance().getReferenceFromUrl("gs://databasedemo-97731.appspot.com/" + username + ".jpg");
                img.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        reference.child("urlname").setValue("empty");
                        heder_userprofile.setImageResource(R.drawable.profile1);

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
    private void pickImage() {

        Intent g=new Intent(Intent.ACTION_PICK);
        g.setType("image/*");
        startActivityForResult(g,Pick_Image_Code);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_Image_Code && resultCode == RESULT_OK) {
            uriname = data.getData();
            Picasso.with(this).load(data.getData()).into(heder_userprofile);
            upLoadeIcone();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void upLoadeIcone() {
        final StorageReference fileRefreance = s_ref.child(username + "." + getFileExtension(uriname));
        fileRefreance.putFile(uriname)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRefreance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("urlname").setValue(String.valueOf(uri));

                            }
                        });
                        Toast.makeText(getApplicationContext(), "successfully upload", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
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

}
