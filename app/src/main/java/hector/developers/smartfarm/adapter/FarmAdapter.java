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
import hector.developers.smartfarm.details.FarmImpleDetailsActivity;
import hector.developers.smartfarm.details.FarmImplementDetailsActivity;
import hector.developers.smartfarm.model.FarmImplements;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder> {
    List<FarmImplements> farmImplementsList;
    Context context;

    public FarmAdapter(List<FarmImplements> farmImplementsList, Context context) {
        this.farmImplementsList = farmImplementsList;
        this.context = context;
    }


    @NonNull
    @Override
    public FarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.farm_lists, parent, false);
        return new FarmAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmAdapter.ViewHolder holder, int position) {
        final FarmImplements farmImplements = farmImplementsList.get(position);
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
                Intent implementIntent = new Intent(context, FarmImpleDetailsActivity.class);
                implementIntent.putExtra("key", farmImplements);
                context.startActivity(implementIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmImplementsList.size();
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
}

