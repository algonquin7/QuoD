package com.appdow.quod;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LogoSplashActivity extends AppCompatActivity {
private static int SPLASHTIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_splash);
        TextView textView = (TextView) findViewById( R.id.textView );
        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             typeface = getResources().getFont(R.font.pricds);
        }
        textView.setTypeface( typeface );
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
