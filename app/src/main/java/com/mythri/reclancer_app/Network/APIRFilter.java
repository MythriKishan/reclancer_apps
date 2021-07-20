package com.mythri.reclancer_app.Network;

import com.mythri.reclancer_app.Model.RecFilter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRFilter {

    @POST("apprec_filter.php")
    @FormUrlEncoded
    Call<RecFilter> R_Filter(@Field("cat") String cat,
                             @Field("pskills") String pskills,
                             @Field("state") int state,
                             @Field("worktype") int worktype


  );
}
