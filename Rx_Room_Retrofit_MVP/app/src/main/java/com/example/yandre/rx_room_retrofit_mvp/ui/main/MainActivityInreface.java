package com.example.yandre.rx_room_retrofit_mvp.ui.main;

import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;
import java.util.List;

public interface MainActivityInreface {
    void displayUsers(List<UsersResponse> userList);
}
