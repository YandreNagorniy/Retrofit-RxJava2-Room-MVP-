package com.example.yandre.rx_room_retrofit_mvp.ui.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yandre.rx_room_retrofit_mvp.R;
import com.example.yandre.rx_room_retrofit_mvp.databinding.ActivityDetailsBinding;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;

import io.reactivex.disposables.Disposable;

public class DetailsActivity extends AppCompatActivity implements DetailsActivityInterface {
    ActivityDetailsBinding binding;
    DetailsActivityPresenterInterface detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        detailsPresenter = new DetailsActivityPresenter(this);

        if (getIntent() != null) {
            String login = (getIntent().getStringExtra("userLogin"));
            detailsPresenter.viewCreated(login);
        }else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUser(UsersResponse user) {
        binding.setUser(user);
        Glide.with(this).load(user.getAvatarUrl()).into(binding.ivUserAvatar);
        binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) detailsPresenter.IsChecked();
            else detailsPresenter.IsUnChecked();
        });
    }
}
