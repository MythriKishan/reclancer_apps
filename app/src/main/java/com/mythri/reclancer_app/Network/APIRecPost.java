package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecPostAd;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRecPost {


    @POST("apprec_postad")
    @FormUrlEncoded
    Call<RecPostAd> RecPostad(@Field("userId") String userId,
                              @Field("token") String token,
                              @Field("name") String name,
                              @Field("email") String email,
                              @Field("mobile") String mobile,
                              @Field("jtitle") String jtitle,
                              @Field("state") int state,
                              @Field("city") String city,
                              @Field("cat") String cat,
                              @Field("newcat") String newcat,
                              @Field("subcategory") String subcategory,
                              @Field("skills") String skills,
                              @Field("sskills") String sskills,
                              @Field("exp") String exp,
                              @Field("address") String address,
                              @Field("prjrates") String prjrates,
                              @Field("prjperiod") String prjperiod,
                              @Field("work_type") int work_type,
                              @Field("desp") String desp,
                              @Field("start_date") String start_date,
                              @Field("end_date") String end_date,
                              @Field("last_date") String last_date,
                              @Field("appemail") String appemail


    );
}
