package com.babu.mytask.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.babu.mytask.BuildConfig;
import com.babu.mytask.data.model.FactData;
import com.babu.mytask.data.model.Response;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Babu on 7/4/2018.
 */
public class RepositoryApi {
    private ApplicationApi applicationApi;
    private static RepositoryApi repository;

    private RepositoryApi() {
        int CONNECTION_TIMEOUT = 60;
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        applicationApi = retrofit.create(ApplicationApi.class);
    }

    public synchronized static RepositoryApi getInstance() {
        if (repository == null) {
            repository = new RepositoryApi();
        }
        return repository;
    }

    public MutableLiveData<Response> loadFactInformation() {
        final MutableLiveData<Response> responseMutableLiveData = new MutableLiveData<>();
        applicationApi.loadFactInformation().enqueue(new Callback<FactData>() {
            @Override
            public void onResponse(Call<FactData> call, retrofit2.Response<FactData> response) {
                if (response.isSuccessful()) {
                    responseMutableLiveData.postValue(Response.success(response.body()));
                } else {
                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        responseMutableLiveData.postValue(Response.error(null, new Throwable("Un authorized error")));
                    } else if (response.code() == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
                        responseMutableLiveData.postValue(Response.error(null, new Throwable("Connection time out")));
                    } else {
                        responseMutableLiveData.postValue(Response.error(null, new Throwable("Something wrong")));
                    }
                }
            }

            @Override
            public void onFailure(Call<FactData> call, Throwable t) {
                responseMutableLiveData.postValue(Response.error(null, new Throwable("Check your Internet Connection")));
            }
        });
        return responseMutableLiveData;
    }

}
