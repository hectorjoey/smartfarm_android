package hector.developers.smartfarm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import hector.developers.smartfarm.R;

public class SuccessPageActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_page);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SuccessPageActivity.this, BuyerDashboardActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 5000);

    }
}