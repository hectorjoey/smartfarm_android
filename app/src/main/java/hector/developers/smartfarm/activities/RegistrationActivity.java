package hector.developers.smartfarm.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ikhiloyaimokhai.nigeriastatesandlgas.Nigeria;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    TextView mLayoutSignIn;

    EditText mLastName, mFirstName, mEmail, mPhone, mPassword;
    Button mRegisterButton;

    RadioGroup userTypeRadio;
    RadioButton rb;

    private Spinner mStateSpinner;
    private String mState;
    private List<String> states;
    private static final int SPINNER_HEIGHT = 500;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mLayoutSignIn = findViewById(R.id.link_login);
//        user info
        mLastName = findViewById(R.id.surname);
        mFirstName = findViewById(R.id.firstname);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mStateSpinner = findViewById(R.id.stateSpinner);
        userTypeRadio = findViewById(R.id.radio_userType);

        resizeSpinner(mStateSpinner, SPINNER_HEIGHT);
        states = Nigeria.getStates();

        //call to method that'll set up state and lga spinner
        setupSpinners();

        mLayoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //registration button
        mRegisterButton = findViewById(R.id.btn_signup);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String firstname = mFirstName.getText().toString().trim();
                final String lastname = mLastName.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();
                final String state = mStateSpinner.getSelectedItem().toString();
                final String userType = ((RadioButton) findViewById(userTypeRadio.getCheckedRadioButtonId())).getText().toString();

                if (TextUtils.isEmpty(lastname)) {
                    mLastName.setError("last name is required");
                    mLastName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(firstname)) {
                    mFirstName.setError("first name is required");
                    mFirstName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mFirstName.setError("Email is required!");
                    mFirstName.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Enter a valid email");
                    mEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;

                }
                if (password.length() < 6) {
                    mPassword.setError("Password length is too short");
                    mPassword.requestFocus();
                    return;
                }
                if (phone.isEmpty() || mPhone.length() != 11) {
                    mPhone.setError("Enter a valid phone number");
                    mPhone.requestFocus();
                    return;
                }

                if (!(mPhone.getText().toString().startsWith("080")
                        || mPhone.getText().toString().startsWith("090") || mPhone.getText().toString().startsWith("081")
                        || mPhone.getText().toString().startsWith("070") || mPhone.getText().toString().startsWith("091"))) {
                    mPhone.setError("Enter a correct phone format!");
                    mPhone.requestFocus();
                    return;

                }

                registerUser(firstname, lastname, phone, email, password,
                        userType, state);
            }
        });
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


    public void onRadioButtonClicked(View view) {
        int radioButtonId = userTypeRadio.getCheckedRadioButtonId();
        rb = findViewById(radioButtonId);
    }

    public void registerUser(String firstname, String lastname, String phone,
                             String email, String password,
                             String userType, String state) {
        //do registration API call
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(firstname, lastname, phone, email, password, userType, state);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(RegistrationActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("Responding ::: " + response);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("throwing " + t);
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
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}
