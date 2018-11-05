package com.example.yandre.rx_room_retrofit_mvp.ui.main;

public interface MainActivityPresenterInterface {
    void loadFromServer();
    void loadFromDatabase();
    void destroyed();
}
