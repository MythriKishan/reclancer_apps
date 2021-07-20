package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecReg;
import com.mythri.reclancer_app.Model.Rec_mobval;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRmobval {

    @POST("reg_mobval")
    @FormUrlEncoded
    Call<Rec_mobval> sendData(@Field("mobile") String mobile_num,
                              @Field("otp") String otpnum);
}

