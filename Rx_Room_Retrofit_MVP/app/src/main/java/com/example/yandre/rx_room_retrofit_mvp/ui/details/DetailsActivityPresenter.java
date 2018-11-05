package com.example.yandre.rx_room_retrofit_mvp.ui.details;

import com.example.yandre.rx_room_retrofit_mvp.App;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;
import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponseDao;
import com.example.yandre.rx_room_retrofit_mvp.network.NetworkClient;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsActivityPresenter implements DetailsActivityPresenterInterface {

    private DetailsActivityInterface detailsActivityInterface;
    private Disposable disposable;
    private UsersResponseDao usersResponseDao;
    private UsersResponse userDb;

    private void setUserDb(UsersResponse userDb) {
        this.userDb = userDb;
    }

    public DetailsActivityPresenter(DetailsActivityInterface detailsActivityInterface) {
        this.detailsActivityInterface = detailsActivityInterface;
        usersResponseDao = App.getInstance().getDatabase().usersResponseDao();
    }

    @Override
    public void viewCreated(String login) {
        usersResponseDao.getUser(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<UsersResponse>() {
                    @Override
                    public void onSuccess(UsersResponse user) {
                        setUserDb(user);
                        detailsActivityInterface.displayUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable = loadUserFromServer(login)
                                .doOnSuccess(DetailsActivityPresenter.this::setUserDb)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(value -> detailsActivityInterface.displayUser(value), Throwable::printStackTrace);
                    }
                });
    }

    private Single<UsersResponse> loadUserFromServer(String login) {
        return NetworkClient.getRetrofit()
                .getUser(login);
    }

    @Override
    public void IsChecked() {
        userDb.checked = true;
        Completable.fromAction(() -> usersResponseDao.insert(userDb))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void IsUnChecked() {
        userDb.checked = false;
        Completable.fromAction(() -> usersResponseDao.delete(userDb.getLogin()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void destroyed() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }
}
