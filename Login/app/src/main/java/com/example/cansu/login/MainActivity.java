package com.example.cansu.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.iangclifton.android.floatlabel.FloatLabel;


public class MainActivity extends AppCompatActivity {

    private TextView txt_title;
    private MediaPlayer mp;
    private Button log_btn;
    private FloatLabel username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mp = new MediaPlayer();


        txt_title = (TextView)findViewById(R.id.text_name);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Regular.otf");
        txt_title.setTypeface(face);

        username = (FloatLabel)findViewById(R.id.text_username);

        password = (FloatLabel)findViewById(R.id.text_password);

        log_btn = (Button)findViewById(R.id.btn_login);

        VideoView video = (VideoView) findViewById(R.id.view_video);

        Uri video_path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);


        video.setVideoURI(video_path);
        video.start();



        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
            }
        });

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getEditText().getText().toString().equals("userlogin") && password.getEditText().getText().toString().equals("mobilhanem")){

                    Toast.makeText(getApplicationContext(),"OK!",Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getApplicationContext(),"NOT OK!",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
