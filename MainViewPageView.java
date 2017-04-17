package com.example.michael.thegardenapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainViewPageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_page_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
    }
    public void openMap(View view) {
        Intent intent = new Intent(MainViewPageView.this, MapsActivity.class);
        startActivity(intent);
    }
    public void openGallary(View view) {
        Intent intent = new Intent(MainViewPageView.this, NewGallaryActivity.class);
        startActivity(intent);
    }
    public void openWebsite(View view) {
        Uri uri = Uri.parse("http://www.botanicsswansea.co.uk"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
