package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.TokenJsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRLogout {
    @POST("apprlogout")
    @FormUrlEncoded
    Call<TokenJsonObject> logOutUser(@Field("token") String token,
                                     @Field("userid") String userid

    );
}
