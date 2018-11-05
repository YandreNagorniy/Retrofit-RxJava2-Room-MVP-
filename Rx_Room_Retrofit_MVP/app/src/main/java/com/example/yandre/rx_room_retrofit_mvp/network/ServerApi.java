package com.example.yandre.rx_room_retrofit_mvp.network;

import com.example.yandre.rx_room_retrofit_mvp.model.UsersResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ServerApi {

    @GET("users")
    Single<List<UsersResponse>> getUserList();

    @GET("users/{username}")
    Single<UsersResponse> getUser(@Path("username") String login);
}
