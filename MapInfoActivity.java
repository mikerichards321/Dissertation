package com.example.michael.thegardenapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MapInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);
    }
    public void openMap(View view) {
        Intent intent = new Intent(MapInfoActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    }

