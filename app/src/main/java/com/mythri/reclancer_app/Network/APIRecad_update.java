package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.Recupdatead;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRecad_update {

    @POST("apprec_update.php")
    @FormUrlEncoded
    Call<Recupdatead> RecUpdatead(

            @Field("ad_id") String ad_id,
            @Field("s_id") int s_id,
            @Field("city_name") String city_name,
            @Field("sub_cat") String sub_cat,
            @Field("rates") String rates,
            @Field("period") String period,
            @Field("desc") String desc,
            @Field("comp_email") String comp_email,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("last_date") String last_date

    );

}
