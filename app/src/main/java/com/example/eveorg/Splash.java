package com.example.eveorg;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (TextView)findViewById(R.id.splash);

        Animation loadIn = AnimationUtils.loadAnimation(this,R.anim.trans_in);
        logo.startAnimation(loadIn);

        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Splash.this, LoginNew.class);
                startActivity(in);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
