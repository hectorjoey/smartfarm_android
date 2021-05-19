package hector.developers.smartfarm.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.FarmImplementListActivity;
import hector.developers.smartfarm.list.ProdListActivity;


public class FarmerDashboardActivity extends AppCompatActivity {
    MaterialCardView mAddProductCardId, mViewMyProductsCardId, mViewFarmImpCardId;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");
        mAddProductCardId = findViewById(R.id.addProductCardId);
        mViewMyProductsCardId = findViewById(R.id.viewMyProductsCardId);
        mViewFarmImpCardId = findViewById(R.id.viewFarmImpCardId);

        mViewFarmImpCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewFarmImplIntent = new Intent(getApplicationContext(), FarmImplementListActivity.class);
                startActivity(viewFarmImplIntent);
                finish();
            }
        });

        mViewMyProductsCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prodIdListIntent = new Intent(getApplicationContext(), ProdListActivity.class);
                startActivity(prodIdListIntent);
                finish();
            }
        });

        mAddProductCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productIntent = new Intent(FarmerDashboardActivity.this, ProductActivity.class);
                startActivity(productIntent);
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
                        Intent intent = new Intent(FarmerDashboardActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}
