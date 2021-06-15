package hector.developers.smartfarm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.FarmListActivity;
import hector.developers.smartfarm.list.ProdListActivity;
import hector.developers.smartfarm.model.FarmImplements;
import hector.developers.smartfarm.model.Products;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateImplementActivity extends AppCompatActivity {
    FarmImplements farmImplements;

    private EditText mUpdateImplementName, mUpdateImplementSize, mUpdateImplementPrice;
    private Spinner mUpdateImplementCategorySpinner;
    private String implementId;
    Button mUpdateImplement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_implement);

        mUpdateImplementName = findViewById(R.id.updateImplementName);
        mUpdateImplementPrice = findViewById(R.id.updateImplementPrice);
        mUpdateImplementSize = findViewById(R.id.updateImplementSize);
        mUpdateImplementCategorySpinner = findViewById(R.id.updateImplementCategorySpinner);
        mUpdateImplement = findViewById(R.id.updateImplement);

        farmImplements = (FarmImplements) getIntent().getSerializableExtra("key");
        assert farmImplements != null;
        implementId = String.valueOf(farmImplements.getId());
        String implementId = String.valueOf(farmImplements.getId());
        System.out.println("Implements Id::: " + implementId);
        System.out.println("Implements Id::: " + implementId);
        mUpdateImplementName.setText(farmImplements.getImplementName());
        mUpdateImplementPrice.setText(farmImplements.getPrice());
        mUpdateImplementSize.setText(farmImplements.getSize());

        setUpUpdateImplementCategory();
        mUpdateImplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String implementName = mUpdateImplementName.getText().toString().trim();
                String size = mUpdateImplementSize.getText().toString().trim();
                String price = mUpdateImplementPrice.getText().toString().trim();
                String implementCategory = mUpdateImplementCategorySpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(implementName)) {
                    mUpdateImplementName.setError("Enter New Implement!");
                    mUpdateImplementName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(size)) {
                    mUpdateImplementSize.setError("Enter New Size!");
                    mUpdateImplementSize.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(price)) {
                    mUpdateImplementPrice.setError("Enter New Price!");
                    mUpdateImplementPrice.requestFocus();
                    return;
                }
                updateImplement();
            }
        });
    }

    private void updateImplement() {
        implementId = String.valueOf(farmImplements.getId());
        RequestBody implementName = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateImplementName.getText()
                        .toString());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateImplementPrice.getText()
                        .toString());
        RequestBody implementCategory = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateImplementCategorySpinner.getSelectedItem().toString());

        RequestBody size = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateImplementSize.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Implement...");
        progressDialog.show();
        Call<FarmImplements> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateImplement(Long.valueOf(implementId), implementName, size, price, implementCategory);
        call.enqueue(new Callback<FarmImplements>() {
            @Override
            public void onResponse(Call<FarmImplements> call, Response<FarmImplements> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Implement Updated!", Toast.LENGTH_SHORT).show();
                    Intent updateIntent = new Intent(getApplicationContext(), FarmListActivity.class);
                    startActivity(updateIntent);
                    System.out.println("Updated Implement::: " + response);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Implement Updated Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Updated Failed::: " + response);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<FarmImplements> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Updating Error::: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setUpUpdateImplementCategory() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> implementCategoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryImplementArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        implementCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mUpdateImplementCategorySpinner.setAdapter(implementCategoryAdapter);
    }
}