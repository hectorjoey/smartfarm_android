package hector.developers.smartfarm.details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

//import com.braintreepayments.cardform.view.CardForm;
//
//import co.paystack.android.Paystack;
//import co.paystack.android.PaystackSdk;
//import co.paystack.android.Transaction;
//import co.paystack.android.model.Card;
//import co.paystack.android.model.Charge;
import com.braintreepayments.cardform.view.CardForm;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.activities.SuccessPageActivity;
import hector.developers.smartfarm.model.FarmImplements;

public class FarmImplementDetailsActivity extends AppCompatActivity {
    FarmImplements farmImplements;
    TextView tvDetailImplementName;
    TextView tvDetailImplementSize;
    TextView tvDetailImplementPrice;
    TextView tvDetailImplementState;
    TextView tvDetailImplementLga;
    TextView tvDetailImplementLocation;
    CardView cardView;
    ImageView mBuy2;
    CardForm cardForm;
    Card card;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public String responseEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_implement_details);
        PaystackSdk.initialize(getApplicationContext());


        pref = getApplicationContext().getSharedPreferences("farm", 0);
        responseEmail = pref.getString("email", null);

        tvDetailImplementName = findViewById(R.id.tvDetailImplementName);
        tvDetailImplementSize = findViewById(R.id.tvDetailImplementQuantity);
        tvDetailImplementPrice = findViewById(R.id.tvDetailImplementPrice);
        tvDetailImplementState = findViewById(R.id.tvDetailImplementState);
        tvDetailImplementLga = findViewById(R.id.tvDetailImplementLga);
        tvDetailImplementLocation = findViewById(R.id.tvDetailImplementLocation);
        cardView = findViewById(R.id.detailCardView);
        mBuy2 = findViewById(R.id.buy2);

        mBuy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog();

            }
        });

        farmImplements = (FarmImplements) getIntent().getSerializableExtra("key");
        assert farmImplements != null;
        tvDetailImplementName.setText(farmImplements.getImplementName());
        tvDetailImplementSize.setText(farmImplements.getSize());
        tvDetailImplementPrice.setText(farmImplements.getPrice());
        tvDetailImplementState.setText(farmImplements.getState());
        tvDetailImplementLga.setText(farmImplements.getLga());
        tvDetailImplementLocation.setText(farmImplements.getLocation());

        cardView.setAnimation(AnimationUtils.loadAnimation(FarmImplementDetailsActivity.this, R.anim.fade_scale));
    }

    private void showPaymentDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment");
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        builder.setNegativeButton("cancel", null);
//
        cardForm = customLayout.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
//                .cardholderName(CardForm.FIELD_REQUIRED)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);

        cardForm.getCardNumber();
        cardForm.getExpirationMonth();
        cardForm.getExpirationYear();
        cardForm.getCvv();
//        cardForm.getCardholderName();


        Button button = customLayout.findViewById(R.id.proceedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Card2", cardForm.getCvv());
                Log.d("Card2", cardForm.getCardNumber());
//                Log.d("Card2", cardForm.getCardholderName());
                Log.d("Card2", cardForm.getExpirationMonth());
                Log.d("Card2", cardForm.getExpirationYear());

//             String cardholderName = cardForm.getCardholderName();

                String cardNumber = cardForm.getCardNumber();
                int expiryMonth = Integer.parseInt(cardForm.getExpirationMonth());
                int expiryYear = Integer.parseInt(cardForm.getExpirationYear());
                String cvv = cardForm.getCvv();

                card = new Card(cardNumber, expiryMonth, expiryYear, cvv);

                if (card.isValid()) {
                    performCharge();
                } else {
                    Toast.makeText(getApplicationContext(), "invalid Card entered", Toast.LENGTH_LONG).show();
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performCharge() {
        int amount = Integer.parseInt(farmImplements.getPrice());
        int newAmount = amount * 100;
        Log.d("amount1", "" + amount);
        Charge charge = new Charge();
        charge.setCard(card);
        charge.setAmount(newAmount);
        charge.setEmail(responseEmail);
        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                String paymentReference = transaction.getReference();
                Toast.makeText(getApplicationContext(), "Payment Successful: " + paymentReference, Toast.LENGTH_LONG).show();
                Intent successIntent = new Intent(getApplicationContext(), SuccessPageActivity.class);
                startActivity(successIntent);
                finish();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                String paymentReference = transaction.getReference();
                Toast.makeText(getApplicationContext(), "Payment Successful: " + paymentReference, Toast.LENGTH_LONG).show();
                Log.d("Transaction::", "????? " + transaction);
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Toast.makeText(getApplicationContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();

                Log.d("Error::", " " + error + " ..." + transaction);

            }
        });
    }

//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setTitle("Want to go back?")
//                .setMessage("Are you sure you want to go back?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent intent = new Intent(FarmImplementDetails.this, FarmImplementDealer.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//                }).create().show();
//    }
}
