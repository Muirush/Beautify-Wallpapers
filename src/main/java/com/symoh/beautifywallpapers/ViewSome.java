package com.symoh.beautifywallpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ViewSome extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_some);
        imageView = findViewById(R.id.imageVSDisplay);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int resId = bundle.getInt("resID");
            imageView.setImageResource(resId);
        }

    }
}