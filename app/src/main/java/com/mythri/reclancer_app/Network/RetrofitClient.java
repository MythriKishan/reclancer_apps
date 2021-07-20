package com.mythri.reclancer_app.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static Retrofit retrofit = null;

   /* OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    //getAccessToken is your own accessToken(retrieve it by saving in shared preference or any other option )
                    if(getToken().isEmpty()) {
                        //PrintLog.error("retrofit 2","Authorization header is already present or token is empty....");
                        return chain.proceed(chain.request());
                    }
                    Request authorisedRequest = chain.request().newBuilder()
                            .addHeader("Authorization", getAccessToken()).build();
                    //PrintLog.error("retrofit 2","Authorization header is added to the url....");
                    return chain.proceed(authorisedRequest);
                }}).build();*/


    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
