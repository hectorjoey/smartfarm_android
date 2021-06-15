package hector.developers.smartfarm.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import hector.developers.smartfarm.Api.RetrofitClient;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.adapter.ImplementAdapter;
import hector.developers.smartfarm.model.FarmImplements;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmImplementListActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mToolbar;
    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<FarmImplements> farmList;
    private ImplementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_implement_list);
        rv = findViewById(R.id.recyclerviewList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mToolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(mToolbar);
        setTitle("");
        loadingBar = new ProgressDialog(this);

        fetchFarmImplements();
    }

    public void fetchFarmImplements() {
        Call<List<FarmImplements>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getFarmImplements();
        call.enqueue(new Callback<List<FarmImplements>>() {
            @Override
            public void onResponse(Call<List<FarmImplements>> call, Response<List<FarmImplements>> response) {
                loadingBar.dismiss();
                farmList = response.body();
                adapter = new ImplementAdapter(farmList, FarmImplementListActivity.this);
                System.out.println(adapter);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<FarmImplements>> call, Throwable t) {

                Toast.makeText(FarmImplementListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}