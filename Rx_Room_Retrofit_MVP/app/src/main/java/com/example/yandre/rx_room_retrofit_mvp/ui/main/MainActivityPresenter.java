package com.example.yandre.rx_room_retrofit_mvp.ui.main;

import com.example.yandre.rx_room_retrofit_mvp.App;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponseDao;
import com.example.yandre.rx_room_retrofit_mvp.network.NetworkClient;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityPresenterInterface {
    MainActivityInreface activityInreface;
    Disposable disposable;
    Disposable disposable2;
    UsersResponseDao usersResponseDao;

    MainActivityPresenter(MainActivityInreface activityInreface) {
        this.activityInreface = activityInreface;
        usersResponseDao = App.getInstance().getDatabase().usersResponseDao();
    }

    @Override
    public void loadFromServer() {
        disposable = getUserList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityInreface::displayUsers, Throwable::printStackTrace);
    }

    @Override
    public void loadFromDatabase() {
        disposable2 = usersResponseDao.getAllUSers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityInreface::displayUsers, Throwable::printStackTrace);

    }

    public Single<List<UsersResponse>> getUserList() {
        return NetworkClient.getRetrofit()
                .getUserList();
    }

    @Override
    public void destroyed() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
        if (disposable2 != null && !disposable2.isDisposed()) disposable2.dispose();
    }
}
