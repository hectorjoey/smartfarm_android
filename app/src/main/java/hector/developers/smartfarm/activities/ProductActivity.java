package hector.developers.smartfarm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ikhiloyaimokhai.nigeriastatesandlgas.Nigeria;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.model.Products;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    Button mSubmitProduct;
    EditText mProductName, mQuantity, mPrice, mLocation;
    ProgressDialog loadingBar;
    private Spinner mStateSpinner, mLgaSpinner, mProductCategorySpinner;

    private String mState, mLga;
    private List<String> states;
    private static final int SPINNER_HEIGHT = 500;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        mSubmitProduct = findViewById(R.id.btn_register_product);
        mProductName = findViewById(R.id.edit_product_name);
        mQuantity = findViewById(R.id.edit_quantity);
        mPrice = findViewById(R.id.edit_text_price);
        mLocation = findViewById(R.id.editText_location);
        mStateSpinner = findViewById(R.id.stateSpinner);
        mLgaSpinner = findViewById(R.id.lgaSpinner);
        mProductCategorySpinner = findViewById(R.id.productCategorySpinner);

        HashMap<String, String> id = getUserId();
        userId = id.get("userId");

        resizeSpinner(mStateSpinner, SPINNER_HEIGHT);
        resizeSpinner(mLgaSpinner, SPINNER_HEIGHT);
        states = Nigeria.getStates();

        //call to method that'll set up state and lga spinner
        setupSpinners();

        mSubmitProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String productName = mProductName.getText().toString().trim();
                final String quantity = mQuantity.getText().toString().trim();
                final String price = mPrice.getText().toString().trim();
                final String location = mLocation.getText().toString().trim();
                final String productCategory = mProductCategorySpinner.getSelectedItem().toString();
                final String state = String.valueOf(mStateSpinner.getSelectedItem());
                final String lga = String.valueOf(mLgaSpinner.getSelectedItem());

                if (TextUtils.isEmpty(productName)) {
                    Toast.makeText(getApplicationContext(), "Enter Name of Product!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(quantity)) {
                    Toast.makeText(getApplicationContext(), "Enter quantity!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(price)) {
                    Toast.makeText(getApplicationContext(), "Enter price!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(getApplicationContext(), "Enter your location!", Toast.LENGTH_SHORT).show();
                }
                registerProduct(productName, quantity, price, state, lga, location, productCategory, Long.valueOf(userId));
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> productCategoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryProductArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        productCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mProductCategorySpinner.setAdapter(productCategoryAdapter);
    }

    public void setupSpinners() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        //populates the quantity spinner ArrayList

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);

        // Specify dropdown layout style - simple list view with 1 item per line
        statesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        statesAdapter.notifyDataSetChanged();
        mStateSpinner.setAdapter(statesAdapter);

        // Set the integer mSelected to the constant values
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mState = (String) parent.getItemAtPosition(position);
                setUpStatesSpinner(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Unknown
            }
        });
    }

    /**
     * method to set up the state spinner
     *
     * @param position current position of the spinner
     */
    private void setUpStatesSpinner(int position) {
        List<String> list = new ArrayList<>(Nigeria.getLgasByState(states.get(position)));
        setUpLgaSpinner(list);
    }

    /**
     * Method to set up the local government areas corresponding to selected states
     *
     * @param lgas represents the local government areas of the selected state
     */
    private void setUpLgaSpinner(List<String> lgas) {

        ArrayAdapter lgaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lgas);
        lgaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        lgaAdapter.notifyDataSetChanged();
        mLgaSpinner.setAdapter(lgaAdapter);

        mLgaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                mLga = (String) parent.getItemAtPosition(position);
                //Toast.makeText(ProductsActivity.this, "state: " + mState + " lga: " + mLga, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void resizeSpinner(Spinner spinner, int height) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            //Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            //set popupWindow height to height
            popupWindow.setHeight(height);

        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }


    public void registerProduct(String productName, String quantity, String price, String state, String lga, String location, String productCategory, Long userId) {
        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Data...");
        progressDialog.show();

        //building retrofit object
        Call<Products> call = RetrofitClient
                .getInstance()
                .getApi()
                .createProduct(productName, quantity, price, state, lga, location, productCategory, userId
                );
        call.enqueue(new Callback<Products>() {
            @Override

            public void onResponse(Call<Products> call, Response<Products> response) {
                Toast.makeText(ProductActivity.this, "Product created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductActivity.this, FarmerDashboardActivity.class);
                startActivity(intent);
                System.out.println("Product Created :::" + response);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Product Creation failed :::" + t.getMessage());
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
                        Intent intent = new Intent(ProductActivity.this, FarmerDashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public HashMap<String, String> getUserId() {
        HashMap<String, String> id = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("userId", Context.MODE_PRIVATE);
        id.put("userId", sharedPreferences.getString("userId", null));
        return id;
    }
}