package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FreelancerReq;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIfreelancer {

    @POST("reg_freelancer")
    @FormUrlEncoded
    Call<FreelancerReq> savePost(@Field("mail") String mail,
                                 @Field("phone") String phone,
                                 @Field("password") String password,
                                 @Field("cpass") String cpass);

}
