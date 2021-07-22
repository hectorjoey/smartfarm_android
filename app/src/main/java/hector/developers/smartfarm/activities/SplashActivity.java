package hector.developers.smartfarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hector.developers.smartfarm.R;

public class SplashActivity extends AppCompatActivity {

    ImageView mSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplash = findViewById(R.id.splash);
        mSplash.setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink));
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3 * 1000);
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}