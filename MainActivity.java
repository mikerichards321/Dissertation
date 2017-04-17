package com.example.michael.thegardenapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isPlay = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button musicbutton = (Button) this.findViewById(R.id.togglemusic);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.melody);
        TextView tv=(TextView)findViewById(R.id.tvClyneHeading);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/future.ttf");
        musicbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isPlay) {
                    v.setBackgroundResource(R.drawable.ic_pause_light);
                } else {
                    v.setBackgroundResource(R.drawable.ic_play_light);
                }

                isPlay = !isPlay; // reverse

                // If the music is playing
                if (mediaPlayer.isPlaying() == true)
                    // Pause the music player
                    mediaPlayer.pause();
                    // If it's not playing
                else
                    // Resume the music player
                    mediaPlayer.start();
            }
        });

        TextView tx = (TextView)findViewById(R.id.tvClyneHeading);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Great Day Personal Use.ttf");

        tx.setTypeface(custom_font);
    }


    public void openMap(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void openWebsite(View view) {
        Uri uri = Uri.parse("http://www.swansea.gov.uk/clyne"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openGallary(View view) {
        Intent intent = new Intent(MainActivity.this, NewGallaryActivity.class);
        startActivity(intent);
    }


    }

