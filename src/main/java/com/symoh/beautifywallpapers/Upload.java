package com.symoh.beautifywallpapers;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mCategory;
    private  String mKey;

    public Upload() {
    }

    public Upload(String name, String imageUrl, String category) {
        if (name.trim().equals("") || category.trim().equals("")){
            name="No Name";
            category = "Image works on both pc and smartphone";
        }

        this.mName = name;
        this.mImageUrl = imageUrl;
        this.mCategory = category;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }
@Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
