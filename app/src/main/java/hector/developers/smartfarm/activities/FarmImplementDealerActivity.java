package hector.developers.smartfarm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.FarmListActivity;
import hector.developers.smartfarm.list.ProductListActivity;

public class FarmImplementDealerActivity extends AppCompatActivity {
    MaterialCardView mAddImplement, mViewImplement, mViewFarmProdCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_implement_dealer);
        mAddImplement = findViewById(R.id.addImpCardId);
        mViewImplement = findViewById(R.id.viewMyImpCardId);
        mViewFarmProdCardId = findViewById(R.id.viewFarmProdCardId);

        mViewFarmProdCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherProductsIntent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(otherProductsIntent);
                finish();
            }
        });

        mAddImplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FarmImplementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mViewImplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent farmListIntent = new Intent(getApplicationContext(), FarmListActivity.class);
                startActivity(farmListIntent);
                finish();
            }
        });

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(FarmImplementDealerActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}