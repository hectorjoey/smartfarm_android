package hector.developers.smartfarm.details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.activities.FarmImplementDealerActivity;
import hector.developers.smartfarm.activities.SuccessPageActivity;
import hector.developers.smartfarm.activities.UpdateImplementActivity;
import hector.developers.smartfarm.activities.UpdateProductActivity;
import hector.developers.smartfarm.list.FarmListActivity;
import hector.developers.smartfarm.model.FarmImplements;

public class FarmImpleDetailsActivity extends AppCompatActivity {
    FarmImplements farmImplements;
    TextView tvDetailImplementName;
    TextView tvDetailImplementSize;
    TextView tvDetailImplementPrice;
    TextView tvDetailImplementState;
    TextView tvDetailImplementLga;
    TextView tvDetailImplementLocation;
    TextView tvImplementCategory;

    AppCompatButton mEditImplement, mDeleteImplement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_imple);
        tvDetailImplementName = findViewById(R.id.tvDetailImplementName);
        tvDetailImplementSize = findViewById(R.id.tvDetailImplementQuantity);
        tvDetailImplementPrice = findViewById(R.id.tvDetailImplementPrice);
        tvDetailImplementState = findViewById(R.id.tvDetailImplementState);
        tvDetailImplementLga = findViewById(R.id.tvDetailImplementLga);
        tvDetailImplementLocation = findViewById(R.id.tvDetailImplementLocation);
        tvImplementCategory = findViewById(R.id.tvDetailImplementCategory);
        mEditImplement = findViewById(R.id.editImplement);
        mDeleteImplement = findViewById(R.id.deleteImplement);

        farmImplements = (FarmImplements) getIntent().getSerializableExtra("key");
        assert farmImplements != null;
        String implementId = String.valueOf(farmImplements.getId());
        tvDetailImplementName.setText(farmImplements.getImplementName());
        tvDetailImplementSize.setText(farmImplements.getSize());
        tvDetailImplementPrice.setText(farmImplements.getPrice());
        tvDetailImplementState.setText(farmImplements.getState());
        tvDetailImplementLga.setText(farmImplements.getLga());
        tvDetailImplementLocation.setText(farmImplements.getLocation());
        tvImplementCategory.setText(farmImplements.getImplementCategory());
        System.out.println("Implement Id ::" + implementId);

        mDeleteImplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImplement(Long.valueOf(implementId));
            }
        });
        mEditImplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editImplementIntent = new Intent(getApplicationContext(), UpdateImplementActivity.class);
                editImplementIntent.putExtra("key", farmImplements);
                startActivity(editImplementIntent);
                finish();
            }
        });
    }

    private void deleteImplement(Long implementId) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(FarmImpleDetailsActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                deleteImplement(Long.valueOf(implementId));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(FarmImpleDetailsActivity.this, FarmListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

}