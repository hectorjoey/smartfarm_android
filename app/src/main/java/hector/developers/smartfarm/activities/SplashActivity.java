package hector.developers.smartfarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hector.developers.smartfarm.R;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    ImageView mSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplash = findViewById(R.id.splash);
        mSplash.setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink));
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);

    }
}
