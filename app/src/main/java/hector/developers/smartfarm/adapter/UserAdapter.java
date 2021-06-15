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
import hector.developers.smartfarm.details.UserDetailsActivity;
import hector.developers.smartfarm.model.Products;
import hector.developers.smartfarm.model.Users;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {
    List<Users> usersList;
    Context context;
    private List<Users> usersListFull;

    public UserAdapter(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        usersListFull = new ArrayList<>(usersList);
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Users users = usersList.get(position);
        holder.tvFirstname.setText(users.getFirstname());
        holder.tvLastname.setText(users.getLastname());
        holder.tvState.setText(users.getState());
        holder.tvPhone.setText(users.getPhone());
        holder.tvUserType.setText(users.getUserType());
//        holder.tvProductCategory.setText(products.getProductCategory());

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
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra("key", users);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstname;
        TextView tvLastname;
        TextView tvPhone;
        TextView tvState;
                TextView tvUserType;
//        TextView tvProductCategory;
        androidx.cardview.widget.CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFirstname = itemView.findViewById(R.id.tvFirstName);
            tvLastname = itemView.findViewById(R.id.tvLastName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvState = itemView.findViewById(R.id.tvState);
            tvUserType = itemView.findViewById(R.id.tvUserType);
//            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            cardView = itemView.findViewById(R.id.userCardView);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Users> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(usersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Users users : usersListFull) {
                    if (users.getUserType().toLowerCase().contains(filterPattern)
                            || users.getState().toLowerCase().contains(filterPattern)
//                            || products.getProductName().toLowerCase().contains(filterPattern)
//                            || products.getProductCategory().toLowerCase().contains(filterPattern)
                            || users.getPhone().toLowerCase().contains(filterPattern)) {
                        filteredList.add(users);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usersList.clear();
            usersList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}