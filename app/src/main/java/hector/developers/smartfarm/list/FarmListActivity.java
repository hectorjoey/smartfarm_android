package hector.developers.smartfarm.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.adapter.FarmAdapter;
import hector.developers.smartfarm.adapter.ProdAdapter;
import hector.developers.smartfarm.adapter.ProductsAdapter;
import hector.developers.smartfarm.model.FarmImplements;
import hector.developers.smartfarm.model.Products;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<FarmImplements> farmImplementsList;
    private FarmAdapter adapter;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_list);
        rv = findViewById(R.id.recyclers);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        HashMap<String, String> id = getUserId();
        userId = id.get("userId");

        fetchDataById(Long.valueOf(userId));
    }

    private void fetchDataById(Long userId) {
        Call<List<FarmImplements>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getImplementByUserId(userId);

        call.enqueue(new Callback<List<FarmImplements>>() {
            @Override
            public void onResponse(Call<List<FarmImplements>> call, Response<List<FarmImplements>> response) {
                farmImplementsList = response.body();
                adapter = new FarmAdapter(farmImplementsList, FarmListActivity.this);
                System.out.println(adapter);
                rv.setAdapter(adapter);
                System.out.println("Listing <<><<" + response);
            }

            @Override
            public void onFailure(Call<List<FarmImplements>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("throwing???" + t.getMessage());
            }
        });
    }

    public HashMap<String, String> getUserId() {
        HashMap<String, String> id = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("userId", Context.MODE_PRIVATE);
        id.put("userId", sharedPreferences.getString("userId", null));
        return id;
    }
}