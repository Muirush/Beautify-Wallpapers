package com.symoh.beautifywallpapers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class imageActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    DatabaseReference databaseReference;
    List<Upload> mUpload;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mDbListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        View myLayout  =findViewById(android.R.id.content);
        Snackbar snackbar =  Snackbar.make(myLayout,"Please Wait", Snackbar.LENGTH_LONG);
        snackbar.setDuration(5000);
        snackbar.setBackgroundTint(Color.BLUE);
        snackbar.show();

        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


//linearLayoutManager = new LinearLayoutManager(this);
//linearLayoutManager.setReverseLayout(true);
//linearLayoutManager.setStackFromEnd(true);
//recyclerView.setLayoutManager(linearLayoutManager);


        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        mUpload = new ArrayList<>();
       firebaseStorage = FirebaseStorage.getInstance();

        imageAdapter = new ImageAdapter(imageActivity.this,mUpload);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnclickListener(imageActivity.this);

        mDbListener =  databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                     Upload upload = dataSnapshot1.getValue(Upload.class);
                     upload.setmKey(dataSnapshot1.getKey());
                     mUpload.add(upload);

                 }

                imageAdapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(imageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });

    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "Normal click at "+position, Toast.LENGTH_SHORT).show();
        Upload upload =  mUpload.get(position);
        String imgUrl = upload.getmImageUrl();
        Intent intent =  new Intent(imageActivity.this, ViewClass.class);
        intent.putExtra("imageId", imgUrl);
        startActivity(intent);


    }

    @Override
    public void TotalDownloads(int position) {
        Toast.makeText(this, "This imaged is downloaded "+position+" times", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Delete(int position) {
//       Upload selectedItem =mUpload.get(position);
//        final String  selectedKey =selectedItem.getmKey();
//        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
//        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                databaseReference.child(selectedKey).removeValue();
//                Toast.makeText(imageActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDbListener);
    }
}

