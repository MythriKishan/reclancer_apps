package com.mythri.reclancer_app.Network;


import com.mythri.reclancer_app.Model.FreeSearch;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIFSearch {

    @POST("appfree_search")
    @FormUrlEncoded
    Call<FreeSearch>FSearchPost(@Field("userId") String userId,
                                @Field("statename") int statename,
                                @Field("worktype") int worktype,
                                @Field("cat") String cat,
                                @Field("skills") String skills



    );
}
