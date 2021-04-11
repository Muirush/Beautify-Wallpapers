package com.symoh.beautifywallpapers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewPager viewPager;
    int  images[]= {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5};
    int currentPageCounter = 0;
    DatabaseHandler databaseHandler;
    private Uri imgUri;
    private Bitmap imgToStore;
    ImageView defImage, myImage;
    int PICK_IMAGE = 1;
    Button btn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myLayout  =findViewById(android.R.id.content);
        Snackbar snackbar =  Snackbar.make(myLayout,"Welcome", Snackbar.LENGTH_LONG);
        snackbar.setDuration(3000);
        snackbar.setBackgroundTint(Color.BLUE);
        snackbar.show();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        btn = findViewById(R.id.onlineBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent goInt = new Intent(MainActivity.this, imageActivity.class);
            startActivity(goInt);


            }
        });

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SliderAdapter(images,MainActivity.this));
        final Handler handler = new Handler();
        final  Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPageCounter == images.length){
                    currentPageCounter = 0;
                }
                viewPager.setCurrentItem(currentPageCounter++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);

            }
        },2500,2500);

        databaseHandler = new DatabaseHandler(this);
        defImage = findViewById(R.id.img2);
        myImage = findViewById(R.id.img3);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });



    }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()){
            case  R.id.mOn:

               if (CheckConnectivity() == true){
                   Intent moveOnline  = new Intent(MainActivity.this, imageActivity.class);
                   startActivity(moveOnline);

               } else{

               }


                //Toast.makeText(MainActivity.this, "Online mode", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mOff:
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
//                Intent moveOff = new Intent(MainActivity.this, showImages.class);
//                startActivity(moveOff);
                //Toast.makeText(MainActivity.this, "Offline mode coming ghsghdgshdfsghdfghs", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.mShare:
                String link =  "Hey Friend, try this wallpaper app: https://play.google.com/store/apps/details?id=com.symoh.beautifywallpapers";
                Intent intention = new Intent();
                intention.setAction(Intent.ACTION_SEND);
                intention.putExtra(Intent.EXTRA_TEXT, link.toString().trim());
                intention.setType("text/plain");
                startActivity(intention);
                Toast.makeText(this, "Thanks for sharing", Toast.LENGTH_SHORT).show();
                break;

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.simon.dailyquotes"));
//                startActivity(intent);
//
            case R.id.mSet:



               Toast.makeText(this, "Settingz", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.mDev:
             Intent tent = new Intent(MainActivity.this, aboutDev.class);
             startActivity(tent);
        }
            return true;
        }

        @Override
        public void onPointerCaptureChanged ( boolean hasCapture){

        }


//    public void Open(View view) {
//        Intent intent = new Intent(MainActivity.this, addImageInOffline.class);
//        startActivity(intent);
//
//    }
    public  boolean CheckConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true){
            View parentLayout  =findViewById(android.R.id.content);
            Snackbar snackbar =  Snackbar.make(parentLayout,"Internet connected", Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.setBackgroundTint(Color.GREEN);
            snackbar.show();
            return true;

        }
        else {
            View parentLayout  =findViewById(android.R.id.content);
            Snackbar snackbar =  Snackbar.make(parentLayout,"You are not connected to internet, ... Please connect to proceed", Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.show();
            return false;
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if ( requestCode == PICK_IMAGE  && resultCode ==RESULT_OK && data != null && data.getData() != null){
                imgUri = data.getData();
                imgToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                defImage.setImageBitmap(imgToStore);

            }

        }catch (Exception exe){
            Toast.makeText(this, exe.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Next(View view) {
        Intent intNext = new Intent(MainActivity.this, ViewSome.class);
        intNext.putExtra("resId",R.drawable.image1);
        startActivity(intNext);
    }

//    public  void InitDb(View view){
//        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//        String defName = "Beautify Wallpapers";
////        ImageView defImage;
////        defImage = findViewById(R.id.img2);
//        databaseHandler.storeImage(new ModelClass(defName.toString(),imgToStore));
//
//    }


}

//        String defName = "Beautify Wallpapers";
////        ImageView defImage;
////        defImage = findViewById(R.id.img2);
//        databaseHandler.storeImage(new ModelClass(defName.toString(),imgToStore));