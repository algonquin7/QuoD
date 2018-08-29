package com.appdow.quod;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoSplashActivity extends AppCompatActivity {
private static int SPLASHTIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_splash);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent intent = new Intent(LogoSplashActivity.this, QuoD.class);
            startActivity(intent);
            finish();
            }
        },SPLASHTIME);
    }
}
