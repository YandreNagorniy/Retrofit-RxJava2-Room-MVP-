package com.example.yandre.rx_room_retrofit_mvp.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yandre.rx_room_retrofit_mvp.R;
import com.example.yandre.rx_room_retrofit_mvp.databinding.CardUserBinding;
import com.example.yandre.rx_room_retrofit_mvp.ui.details.DetailsActivity;
import com.example.yandre.rx_room_retrofit_mvp.ui.main.MainActivity;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersHolder> {
    private List<UsersResponse> usersList;
    private Context context;

    public UserAdapter(List<UsersResponse> users, MainActivity context) {
        usersList = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.card_user, parent, false);

        return new UsersHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UsersHolder holder, int position) {
        holder.binding.setUser(usersList.get(position));
        Glide.with(context)
                .load(usersList.get(position).getAvatarUrl())
                .into(holder.binding.ivUserAvatar);

        //обработка нажатий на item
        holder.itemView.setOnClickListener(v -> {
            String login = usersList.get(position).getLogin();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("userLogin", login);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder {
        CardUserBinding binding;

        UsersHolder(CardUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
