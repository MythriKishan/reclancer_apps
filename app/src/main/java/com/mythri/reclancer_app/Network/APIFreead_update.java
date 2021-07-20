package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.Freeupdatead;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFreead_update {


    @POST("appfree_update.php")
    @FormUrlEncoded
    Call<Freeupdatead> FreeUpdatead(

            @Field("ad_id") String ad_id,
            @Field("token") String token,
            @Field("s_id") int s_id,
            @Field("cname") String cname,
            @Field("sub_cat") String sub_cat,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("p_t") String p_t,
            @Field("prates") String prates,
            @Field("pref") String pref
    );



}
