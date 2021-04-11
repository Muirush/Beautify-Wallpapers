package com.symoh.beautifywallpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class aboutDev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);
    }

    public void github(View view) {
        //https://github.com/Muirush
        Uri uri = Uri.parse("https://github.com/Muirush");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void gmail(View view) {
//        Uri linkMail = Uri.parse("https://mail.google.com");
//        Intent openGmail = new Intent(Intent.ACTION_VIEW, linkMail);
//        startActivity(openGmail);
//        Intent intSymoh  = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto","rapturesymon@gmail.com", null));
//        startActivity(intSymoh);

//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
//        startActivity(intent);

        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rapturesimon@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Any subject if you want");
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
        else
            Toast.makeText(this,"Gmail App is not installed",Toast.LENGTH_SHORT).show();
    }

    public void openTwitter(View view) {
        Uri tweet = Uri.parse("https://twitter.com/Symoh_Muiruri");
        Intent intTweet = new Intent(Intent.ACTION_VIEW, tweet);

        startActivity(intTweet);
    }
}
