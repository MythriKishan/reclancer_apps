package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FMLogin;
import com.mythri.reclancer_app.Model.FM_otpLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFMLoginOTP {
    @POST("app_fmlogin")
    @FormUrlEncoded
    Call<FM_otpLogin> m_otplogin(@Field("mobile") String mobile,
                                 @Field("otp") String otp
    );
}
