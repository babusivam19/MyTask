package com.babu.mytask.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.babu.mytask.data.model.Response;
import com.babu.mytask.data.repository.RepositoryApi;

/**
 * Created by Babu on 04/07/2018.
 */
public class MainActivityViewModel extends ViewModel {
    private RepositoryApi repositoryApi;
    private MutableLiveData<Response> mutableLiveData;

    public MainActivityViewModel() {
        repositoryApi = RepositoryApi.getInstance();
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<Response>();
            loadInformation();
        }
    }

    public LiveData<Response> loadInformation() {
        repositoryApi.loadFactInformation().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                mutableLiveData.setValue(response);
            }
        });
        return mutableLiveData;
    }
}
