package com.symoh.beautifywallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewClass extends AppCompatActivity {
    Button btOffline, btWallpaper, btShare, btDownload;
    ImageView dispImage;
    TextView dispText;
    Bitmap bitmap, bitmapSymo;
    String folderName;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    DatabaseHandler databaseHandler;
    String imgName = "Beautify_Offline";
    private  Uri myUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Image Detail");

        btOffline = findViewById(R.id.Voffline);
        btWallpaper = findViewById(R.id.Vwallpaper);
        btShare = findViewById(R.id.Vshare);
        btDownload = findViewById(R.id.Vdownload);
        dispImage = findViewById(R.id.imageDisplay);
        //dispText = findViewById(R.id.textDisp);
        databaseHandler = new DatabaseHandler(this);

        //getting image from the previous activity
        String url = getIntent().getStringExtra("imageId");
        Picasso.get().load(url).into(dispImage);



        btOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // folderName = dispText.getText().toString().trim();

                //bitmapSymo = ((BitmapDrawable)dispImage.getDrawable()).getBitmap();

                //String imgName = "Beautify_Offline";
                //Toast.makeText(ViewClass.this, "Offline feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });


        btWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = ((BitmapDrawable)dispImage.getDrawable()).getBitmap();
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wallpaperManager.setBitmap (bitmap);
                    Toast.makeText(ViewClass.this, "Image set as wallpaper", Toast.LENGTH_SHORT).show();

                }catch (Exception ex){
                    Toast.makeText(ViewClass.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmap = ((BitmapDrawable)dispImage.getDrawable()).getBitmap();
                MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"BeuatifyWallpaper", null);
                Toast.makeText(ViewClass.this, "Image Saved", Toast.LENGTH_SHORT).show();
                //haha

            }
        });
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = dispImage.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + "BeautifyWallpapers.jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true,false);
                    final  Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoUri = FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file);

                    intent.putExtra(Intent.EXTRA_STREAM,photoUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");
                    startActivity(Intent.createChooser(intent, "Share image Via..."));


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//

            }
        });


    }



    public void StoreImage(View view) {
        bitmapSymo = ((BitmapDrawable)dispImage.getDrawable()).getBitmap();
        if (  dispImage.getDrawable() != null  ){
            databaseHandler.storeImage(new ModelClass(imgName,bitmapSymo));



        }
        else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }




}
