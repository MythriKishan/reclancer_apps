package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FProfileUpdate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFProfile {

    @POST("appfProfile_update")
    @FormUrlEncoded
    Call<FProfileUpdate> savePost(@Field("userId") String userId,
                                  @Field("token") String token,
                                  @Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("mobilenum") String mobilenum,
                                  @Field("emailid") String emailid,
                                  @Field("adrs") String adrs,
                                  @Field("st") int st,
                                  @Field("ci") String ci
    );

}
