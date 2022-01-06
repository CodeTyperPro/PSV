package com.example.psv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    final private int SPLASH_SCREEN = 3000;

    Animation topAnimation, bottomAnimation;
    ImageView imgLogo;
    TextView txtLogoTitle;
    TextView txtPowered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        txtLogoTitle = (TextView) findViewById(R.id.txtLogoTitle);
        txtPowered = (TextView) findViewById(R.id.txt_powered);

        imgLogo.setAnimation(topAnimation);
        txtLogoTitle.setAnimation(bottomAnimation);
        txtPowered.setAnimation(bottomAnimation);

        topAnimation.start();
        bottomAnimation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}