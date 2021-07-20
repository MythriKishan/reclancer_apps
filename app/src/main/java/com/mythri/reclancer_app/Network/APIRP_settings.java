package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecPSet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRP_settings {
    @POST("appr_privacy")
    @FormUrlEncoded
    Call<RecPSet> RPPost(@Field("userId") String userId,
                         @Field("email") String email,
                         @Field("mobile") String mobile,
                         @Field("photo") String photo

    );
}
