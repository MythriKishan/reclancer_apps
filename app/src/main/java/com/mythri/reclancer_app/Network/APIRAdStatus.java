package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecAdStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRAdStatus {
    @POST("app_r_changestatus")
    @FormUrlEncoded
    Call<RecAdStatus> R_statusupdate(@Field("ad_id") String ad_id,
                                     @Field("st") String st
    );
}
