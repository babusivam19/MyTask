package com.babu.mytask.data.repository;

import com.babu.mytask.data.model.FactData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Babu on 7/4/2018.
 */
public interface ApplicationApi {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<FactData> loadFactInformation();
}
