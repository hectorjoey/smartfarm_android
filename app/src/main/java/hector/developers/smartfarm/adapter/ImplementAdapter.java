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
import hector.developers.smartfarm.details.FarmImplementDetailsActivity;
import hector.developers.smartfarm.model.FarmImplements;

public class ImplementAdapter extends RecyclerView.Adapter<ImplementAdapter.ViewHolder> implements Filterable {
    List<FarmImplements> farmList;
    Context context;
    private List<FarmImplements> farmImplementsListFull;

    public ImplementAdapter(List<FarmImplements> farmList, Context context) {
        this.farmList = farmList;
        this.context = context;
        farmImplementsListFull = new ArrayList<>(farmList);
    }

    @NonNull
    @Override
    public ImplementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.implement_lists, parent, false);
        return new ImplementAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImplementAdapter.ViewHolder holder, int position) {
        final FarmImplements farmImplements = farmList.get(position);
        holder.tvImplementName.setText(farmImplements.getImplementName());
        holder.tvImplementSize.setText(farmImplements.getSize());
        holder.tvImplementPrice.setText(farmImplements.getPrice());
        holder.tvImplementState.setText(farmImplements.getState());
        holder.tvImplementCategory.setText(farmImplements.getImplementCategory());
        holder.tvImplementLocation.setText(farmImplements.getLocation());

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
                Intent implementIntent = new Intent(context, FarmImplementDetailsActivity.class);
                implementIntent.putExtra("key", farmImplements);
                context.startActivity(implementIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvImplementName;
        private TextView tvImplementSize;
        private TextView tvImplementPrice;
        private TextView tvImplementState;
        private TextView tvImplementCategory;
        private TextView tvImplementLocation;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvImplementName = itemView.findViewById(R.id.tvImplementName);
            tvImplementSize = itemView.findViewById(R.id.tvImplementSize);
            tvImplementPrice = itemView.findViewById(R.id.tvImplementPrice);
            tvImplementState = itemView.findViewById(R.id.tvImplementState);
            tvImplementCategory = itemView.findViewById(R.id.tvImplementCategory);
            tvImplementLocation = itemView.findViewById(R.id.tvImplementLocation);
            cardView = itemView.findViewById(R.id.implementCardView);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FarmImplements> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(farmImplementsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FarmImplements farmImplements : farmImplementsListFull) {
                    if (farmImplements.getImplementName().toLowerCase().contains(filterPattern)
                            || farmImplements.getPrice().toLowerCase().contains(filterPattern)
                            || farmImplements.getState().toLowerCase().contains(filterPattern)
                            || farmImplements.getImplementCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(farmImplements);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            farmList.clear();
            farmList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}