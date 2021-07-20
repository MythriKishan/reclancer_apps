package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecReg;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRecReg {

    @POST("reg_recruiter")
    @FormUrlEncoded
    Call<RecReg> savePost(@Field("mail") String mail,
                          @Field("mobile") String mobile,
                          @Field("password") String password,
                          @Field("cpass") String cpass);

}
