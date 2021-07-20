package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FLoginRes;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFreeLogin {
    @POST("appflogin")
    @FormUrlEncoded
    Call<FLoginRes> LoginPost(@Field("mail") String mail,
                              @Field("password") String password
    );
}