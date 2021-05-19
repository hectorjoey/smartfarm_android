package hector.developers.smartfarm.details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.activities.UpdateProductActivity;
import hector.developers.smartfarm.list.ProdListActivity;
import hector.developers.smartfarm.model.Products;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdDetailsActivity extends AppCompatActivity {
    Products products;
    TextView tvDetailProductName;
    TextView tvDetailQuantity;
    TextView tvDetailPrice;
    TextView tvDetailState;
    TextView tvDetailLocation;
    TextView tvProductCategory;

    Button mEdit, mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);
        tvDetailProductName = findViewById(R.id.tvDetailProductNameOne);
        tvDetailQuantity = findViewById(R.id.tvDetailQuantityOne);
        tvDetailPrice = findViewById(R.id.tvDetailPriceOne);
        tvDetailState = findViewById(R.id.tvDetailStateOne);
        tvDetailLocation = findViewById(R.id.tvDetailLocationOne);
        tvProductCategory = findViewById(R.id.tvDetailCategoryOne);
        mEdit = findViewById(R.id.editProductButton);
        mDelete = findViewById(R.id.deleteButton);

        products = (Products) getIntent().getSerializableExtra("key");
        assert products != null;
        String productsId = String.valueOf(products.getId());
        tvDetailProductName.setText(products.getProductName());
        tvDetailQuantity.setText(products.getQuantity());
        tvDetailPrice.setText(products.getPrice());
        tvDetailState.setText(products.getState());
        tvDetailLocation.setText(products.getLocation());
        tvProductCategory.setText(products.getProductCategory());


        System.out.println("Product Id::: " + productsId);

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(Long.valueOf(productsId));
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProductIntent = new Intent(getApplicationContext(), UpdateProductActivity.class);
                editProductIntent.putExtra("key", products);
                startActivity(editProductIntent);
                finish();
            }
        });
    }

    private void deleteProduct(Long productId) {
        Call<Products> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteProduct(productId);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent deleteIntent = new Intent(getApplicationContext(), ProdListActivity.class);
                    startActivity(deleteIntent);
                    finish();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
                        Intent intent = new Intent(ProdDetailsActivity.this, ProdListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}