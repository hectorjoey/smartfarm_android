package hector.developers.smartfarm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;

import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.FarmImplementListActivity;
import hector.developers.smartfarm.list.FarmListActivity;
import hector.developers.smartfarm.list.ProductListActivity;

public class FarmImplementDealerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    MaterialCardView mAddImplement, mViewImplement, mViewFarmProdCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_implement_dealer);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Implement Dealer Dashboard");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Intent logOutIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(logOutIntent);
        finish();
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