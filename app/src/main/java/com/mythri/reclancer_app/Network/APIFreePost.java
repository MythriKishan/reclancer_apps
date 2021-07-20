package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.FreePostAd;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFreePost {

    @POST("appfree_postad")
    @FormUrlEncoded
    Call<FreePostAd> FreePostad(@Field("userId") String userId,
                                @Field("token") String token,
                                @Field("name") String name,
                                @Field("email") String email,
                                @Field("mobile") String mobile,
                                @Field("ptitle") String ptitle,
                                @Field("gender") int gender,
                                @Field("state") int state,
                                @Field("city") String city,
                                @Field("category") String category,
                                @Field("newcat") String newcat,
                                @Field("subcategory") String subcategory,
                                @Field("workloc") int workloc,
                                @Field("skills") String skills,
                                @Field("sskills") String sskills,
                                @Field("exp") String exp,
                                @Field("address") String address,
                                @Field("prjrates") String prjrates,
                                @Field("pref") String pref,
                                @Field("start_date") String start_date,
                                @Field("end_date") String end_date


    );
}
