package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FreePSet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFP_settings {

    @POST("appf_privacy")
    @FormUrlEncoded
    Call<FreePSet> FPPost(@Field("userId") String userId,
                          @Field("token") String token,
                            @Field("email") String email,
                            @Field("mobile") String mobile,
                            @Field("photo") String photo

    );
}
