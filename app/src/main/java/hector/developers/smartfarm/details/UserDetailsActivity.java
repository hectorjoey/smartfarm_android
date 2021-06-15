package hector.developers.smartfarm.details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.ProdListActivity;
import hector.developers.smartfarm.list.UsersList;
import hector.developers.smartfarm.model.Products;
import hector.developers.smartfarm.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    Users users;
    TextView detailFirstname;
    TextView detailLastname;
    TextView detailState;
    TextView detailUserType;
    TextView detailPhone;
    TextView detailEmail;

    String userId;

    AppCompatButton mDeleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        detailFirstname = findViewById(R.id.detailFirstname);
        detailLastname = findViewById(R.id.detailsLastname);
        detailState = findViewById(R.id.detailsState);
        detailUserType = findViewById(R.id.detailUserType);
        detailPhone = findViewById(R.id.detailPhone);
        detailEmail = findViewById(R.id.detailEmail);

        mDeleteUser = findViewById(R.id.deleteButton);

        users = (Users) getIntent().getSerializableExtra("key");
        assert users != null;
        userId = String.valueOf(users.getId());
        System.out.println("Request ID: " + userId);

        System.out.println("User Id:" + userId);
        detailFirstname.setText(users.getFirstname());
        detailLastname.setText(users.getLastname());
        detailState.setText(users.getState());
        detailUserType.setText(users.getUserType());
        detailPhone.setText(users.getPhone());
        detailEmail.setText(users.getEmail());

        mDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UserDetailsActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        deleteUser(Long.valueOf(userId));
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
        });
    }

    private void deleteUser(Long userId) {
        Call<Users> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteUser(userId);
        System.out.println("Users Id >>> " + userId);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent deleteIntent = new Intent(getApplicationContext(), UsersList.class);
                    startActivity(deleteIntent);
                    System.out.println("DEleting REsponse{{} " +  response);
                    finish();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to delete!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    System.out.println("Error REsponse{{} " +  response);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
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
                        Intent intent = new Intent(UserDetailsActivity.this, UsersList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}