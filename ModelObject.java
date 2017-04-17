package com.example.michael.thegardenapplication;

/**
 * Created by micha on 16/03/2017.
 */

public enum ModelObject {

    MAP(R.string.map, R.layout.activity_map_info),
    GALLARY(R.string.gallary, R.layout.activity_gallary_info),
    WEBSITE(R.string.website, R.layout.activity_website_info);


    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
