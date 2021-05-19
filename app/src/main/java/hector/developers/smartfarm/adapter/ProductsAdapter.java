package hector.developers.smartfarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import hector.developers.smartfarm.R;
import hector.developers.smartfarm.details.ProductDetailsActivity;
import hector.developers.smartfarm.model.Products;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements Filterable {
    List<Products> productsList;
    Context context;
    private List<Products> productsListFull;

    public ProductsAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
        productsListFull = new ArrayList<>(productsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Products products = productsList.get(position);
        holder.tvProductName.setText(products.getProductName());
        holder.tvQuantity.setText(products.getQuantity());
        holder.tvPrice.setText(products.getPrice());
        holder.tvState.setText(products.getState());
        holder.tvLocation.setText(products.getLocation());

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
                Intent intent = new Intent(context, ProductDetailsActivity.class);
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
        private TextView tvProductName;
        private TextView tvQuantity;
        private TextView tvPrice;
        private TextView tvState;
        private TextView tvLocation;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvState = itemView.findViewById(R.id.tvState);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Products> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Products products : productsListFull) {
                    if (products.getLocation().toLowerCase().contains(filterPattern)
                            || products.getProductName().toLowerCase().contains(filterPattern)
                            || products.getProductCategory().toLowerCase().contains(filterPattern)
                            || products.getPrice().toLowerCase().contains(filterPattern)) {
                        filteredList.add(products);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productsList.clear();
            productsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}