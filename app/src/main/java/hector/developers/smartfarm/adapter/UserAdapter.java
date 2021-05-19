package hector.developers.smartfarm.adapter;

import android.content.Context;
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
import hector.developers.smartfarm.model.Products;
import hector.developers.smartfarm.model.Users;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<Users> userList;
    Context context;

    public UserAdapter(List<Users> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Users users = userList.get(position);
        holder.tvFirstName.setText(users.getFirstname());
        holder.tvLastName.setText(users.getLastname());
        holder.tvPhone.setText(users.getPhone());
        holder.tvEmail.setText(users.getEmail());
        holder.tvUserType.setText(users.getUserType());
        holder.tvState.setText(users.getState());

//        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
//        if (position % 2 == 1) {
//            holder.cardView.setBackgroundColor(Color.parseColor("#E8EAF6"));
//            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        } else {
//            holder.cardView.setBackgroundColor(Color.parseColor("#BBDEFB"));
//            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
//        }


//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UsersList.class);
//                intent.putExtra("key", products);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLastName;
        private TextView tvFirstName;
        private TextView tvPhone;
        private TextView tvEmail;
        private TextView tvUserType;
        private TextView tvState;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
//            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvUserType = itemView.findViewById(R.id.tvUserType);
            tvState = itemView.findViewById(R.id.tvState);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}