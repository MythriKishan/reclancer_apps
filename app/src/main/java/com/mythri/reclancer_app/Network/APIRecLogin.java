package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecLoginRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRecLogin {

    @POST("applogin")
    @FormUrlEncoded
    Call<RecLoginRes> LoginPost(@Field("mail") String mail,
                                @Field("password") String password
    );
}
