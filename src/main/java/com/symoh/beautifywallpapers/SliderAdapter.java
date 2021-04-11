package com.symoh.beautifywallpapers;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
     int [] images;
    LayoutInflater layoutInflater;
    Context context;

    public SliderAdapter(int[] images,  Context context) {
        this.images = images;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myLayout = layoutInflater.inflate(R.layout.slider_images,container,false);
        ImageView kImageView = myLayout.findViewById(R.id.sImageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            kImageView.setImageDrawable(context.getDrawable(images[position]));
        }else {
            kImageView.setImageDrawable(context.getResources().getDrawable(images[position]));
        }
        container.addView(myLayout);

        return myLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
