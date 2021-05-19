package hector.developers.smartfarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hector.developers.smartfarm.R;
import hector.developers.smartfarm.details.ProdDetailsActivity;
import hector.developers.smartfarm.model.Products;

public class ProdAdapter extends RecyclerView.Adapter<ProdAdapter.ViewHolder> {
    List<Products> productsList;
    Context context;

    public ProdAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prod_list, parent, false);
        return new ProdAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdAdapter.ViewHolder holder, int position) {
        final Products products = productsList.get(position);
        holder.tvProductName.setText(products.getProductName());
        holder.tvQuantity.setText(products.getQuantity());
        holder.tvPrice.setText(products.getPrice());
        holder.tvState.setText(products.getState());
        holder.tvLocation.setText(products.getLocation());
        holder.tvProductCategory.setText(products.getProductCategory());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.cardView.setBackgroundColor(Color.parseColor("#E8EAF6"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("#BBDEFB"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProdDetailsActivity.class);
                intent.putExtra("key", products);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        TextView tvQuantity;
        TextView tvPrice;
        TextView tvState;
        TextView tvLocation;
        TextView tvProductCategory;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvState = itemView.findViewById(R.id.tvState);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}