package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FreeAdStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFAdStatus {

    @POST("app_f_changestatus")
    @FormUrlEncoded
    Call<FreeAdStatus> F_statusupdate(@Field("ad_id") String ad_id,
                                      @Field("st") String st
    );
}
