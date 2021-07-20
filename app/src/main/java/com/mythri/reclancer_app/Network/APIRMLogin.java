package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FMLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRMLogin {
    @POST("apprmlogin")
    @FormUrlEncoded
    Call<FMLogin> m_login(@Field("mobile") String mobile
    );
}
