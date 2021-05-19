package hector.developers.smartfarm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.model.Users;
import hector.developers.smartfarm.utils.SessionManagement;
import hector.developers.smartfarm.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText mEmail, mPassword;
    Button mLogin;
    TextView textInputLayout;
    ProgressDialog loadingBar;
    String responseEmail;
    SharedPreferences pref;
    Editor editor;
    private Util util;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("farm", 0);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mLogin = findViewById(R.id.login);
        loadingBar = new ProgressDialog(this);
        textInputLayout = findViewById(R.id.regLink);
        util = new Util();

        sessionManagement = new SessionManagement(this);

        sessionManagement.getLoginEmail();
        sessionManagement.getLoginPassword();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (util.isNetworkAvailable(getApplicationContext())) {
                    if (email.isEmpty()) {
                        mEmail.setError("Email is required!");
                        mEmail.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        mEmail.setError("Enter a valid email!");
                        mEmail.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        mPassword.setError("Password required!");
                        mPassword.requestFocus();
                        return;
                    }

                    if (password.length() < 6) {
                        mPassword.setError("Password should be at least 6 character long!");
                        mPassword.requestFocus();
                    }
                    loginUser(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Check your network connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }

    public void loginUser(String email, String password) {
        //do registration API call
        Call<Users> call = RetrofitClient
                .getInstance()
                .getApi().login(email, password);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Users users = response.body();
                    assert users != null;
                    System.out.println("responding >>>> " + response.body());
                    System.out.println("responding ... " + response);
                    if (users.getUserType().equalsIgnoreCase("Buyer")) {
                        progressDialog.dismiss();
                        responseEmail = response.body().getEmail();
                        editor = pref.edit();
                        editor.putString("email", responseEmail);
                        editor.commit();
                        Log.d("Email:: ", " " + response.body().getEmail());
                        Intent intent = new Intent(LoginActivity.this, BuyerDashboardActivity.class);
                        intent.putExtra("Buyer", response.body().getUserType());
                        startActivity(intent);
                        intent.putExtra("userId ", users.getId());
                        saveUserId(users.getId());
                        Toast.makeText(LoginActivity.this, "Login successfully as a Buyer!", Toast.LENGTH_SHORT).show();
                        sessionManagement.setLoginEmail(email);
                        sessionManagement.setLoginPassword(password);
                    } else if (users.getUserType().equalsIgnoreCase("Implement Dealer")) {
                        Intent intent = new Intent(LoginActivity.this, FarmImplementDealerActivity.class);
                        progressDialog.dismiss();
                        intent.putExtra("Implement Dealer", response.body().getUserType());
                        startActivity(intent);
                        intent.putExtra("userId ", users.getId());
                        saveUserId(users.getId());
                        Toast.makeText(LoginActivity.this, "Login successfully as a Dealer!", Toast.LENGTH_SHORT).show();
                        sessionManagement.setLoginEmail(email);
                        sessionManagement.setLoginPassword(password);
                    } else if (users.getUserType().equalsIgnoreCase("Admin")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        progressDialog.dismiss();
                        intent.putExtra("Admin", response.body().getUserType());
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login successfully as an Admin!", Toast.LENGTH_SHORT).show();
                        sessionManagement.setLoginEmail(email);
                        sessionManagement.setLoginPassword(password);
                    } else if (users.getUserType().equalsIgnoreCase("Farmer")) {
                        Intent intent = new Intent(LoginActivity.this, FarmerDashboardActivity.class);
                        progressDialog.dismiss();
                        intent.putExtra("Farmer", response.body().getUserType());
                        intent.putExtra("userId ", users.getId());
                        saveUserId(users.getId());
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login successfully as an Farmer!", Toast.LENGTH_SHORT).show();
                        sessionManagement.setLoginEmail(email);
                        sessionManagement.setLoginPassword(password);
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("throwing ..." + t);
                progressDialog.dismiss();
            }
        });
    }

    public void saveUserId(Long id) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("userId", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.putString("userId", id + "");
        edit.apply();
    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
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
}