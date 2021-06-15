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
import hector.developers.smartfarm.list.ProdListActivity;
import hector.developers.smartfarm.model.Products;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class UpdateProductActivity extends AppCompatActivity {
    Products products;
    private EditText mUpdateProductName, mUpdateQuantity, mUpdatePrice;
    private Spinner mUpdateCategorySpinner;
    private String productId;
    Button mUpdateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        mUpdateProductName = findViewById(R.id.updateProductName);
        mUpdateQuantity = findViewById(R.id.updateQuantity);
        mUpdatePrice = findViewById(R.id.updatePrice);
        mUpdateCategorySpinner = findViewById(R.id.updateCategorySpinner);
        mUpdateProduct = findViewById(R.id.updateProduct);

        products = (Products) getIntent().getSerializableExtra("key");
        assert products != null;
        productId = String.valueOf(products.getId());
        String productsId = String.valueOf(products.getId());
        System.out.println("Products Id::: " + productId);
        System.out.println("Product Id::: " + productsId);
        mUpdateProductName.setText(products.getProductName());
        mUpdateQuantity.setText(products.getQuantity());
        mUpdatePrice.setText(products.getPrice());

        setUpUpdateProductCategory();
        mUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = mUpdatePrice.getText().toString().trim();
                String quantity = mUpdateQuantity.getText().toString().trim();
                String productName = mUpdateProductName.getText().toString().trim();
                String productCategory = mUpdateCategorySpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(price)) {
                    mUpdatePrice.setError("Enter New Price!");
                    mUpdatePrice.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(quantity)) {
                    mUpdateQuantity.setError("Enter New Quantity!");
                    mUpdateQuantity.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(productName)) {
                    mUpdateProductName.setError("Enter New Name of Product!");
                    mUpdateProductName.requestFocus();
                    return;
                }
                updateProduct();
            }
        });
    }

    private void setUpUpdateProductCategory() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> productCategoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryProductArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        productCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mUpdateCategorySpinner.setAdapter(productCategoryAdapter);
    }

    private void updateProduct() {
        productId = String.valueOf(products.getId());
        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateProductName.getText()
                        .toString());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"),
                mUpdatePrice.getText()
                        .toString());
        RequestBody productCategory = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateCategorySpinner.getSelectedItem().toString());

        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"),
                mUpdateQuantity.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Product...");
        progressDialog.show();
        Call<Products> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateProduct(Long.valueOf(productId), productName, quantity, price, productCategory);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Product Updated!", Toast.LENGTH_SHORT).show();
                    Intent updateIntent = new Intent(getApplicationContext(), ProdListActivity.class);
                    startActivity(updateIntent);
                    System.out.println("Product Updated ::: " + response);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Product Updating Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Updating Failed::: " + response);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Updating Error::: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}