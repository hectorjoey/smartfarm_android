package hector.developers.smartfarm.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import hector.developers.smartfarm.R;
import hector.developers.smartfarm.list.FarmImplementListActivity;
import hector.developers.smartfarm.list.ProductListActivity;

public class MainActivity extends AppCompatActivity {
    MaterialCardView mViewProductsCardId, mViewImplementCardId, mViewUsersCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewProductsCardId = findViewById(R.id.viewProdCardId);
        mViewImplementCardId = findViewById(R.id.viewImpCardId);

        mViewImplementCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewUsersIntent = new Intent(getApplicationContext(), FarmImplementListActivity.class);
                startActivity(viewUsersIntent);
                finish();
            }
        });

        mViewProductsCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewProductsIntent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(viewProductsIntent);
                finish();
            }
        });
    }
}
