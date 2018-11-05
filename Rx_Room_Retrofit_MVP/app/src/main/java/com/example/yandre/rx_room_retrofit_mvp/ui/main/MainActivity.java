package com.example.yandre.rx_room_retrofit_mvp.ui.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yandre.rx_room_retrofit_mvp.R;
import com.example.yandre.rx_room_retrofit_mvp.adapters.UserAdapter;
import com.example.yandre.rx_room_retrofit_mvp.databinding.ActivityMainBinding;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityInreface {
    private ActivityMainBinding binding;
    MainActivityPresenterInterface mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
         mainPresenter = new MainActivityPresenter(this);

        binding.recyclerList.setLayoutManager(new LinearLayoutManager(this));
        mainPresenter.loadFromServer();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.action_fromServer:
                    mainPresenter.loadFromServer();
                    break;
                case R.id.action_fromDb:
                    mainPresenter.loadFromDatabase();
                    break;
            }
            return true;
        });
    }

    @Override
    public void displayUsers(List<UsersResponse> userList) {
        if (userList != null) {
            RecyclerView.Adapter adapter = new UserAdapter(userList, MainActivity.this);
            binding.recyclerList.setAdapter(adapter);
        } else Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         mainPresenter.destroyed();
    }
}
