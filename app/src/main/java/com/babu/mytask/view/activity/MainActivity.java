package com.babu.mytask.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.babu.mytask.R;
import com.babu.mytask.data.model.FactData;
import com.babu.mytask.data.model.InformationData;
import com.babu.mytask.data.model.Response;
import com.babu.mytask.view.adapter.InformationListAdapter;
import com.babu.mytask.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Babu on 04/07/2018.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_information_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    MainActivityViewModel viewModule;
    InformationListAdapter informationListAdapter;
    ArrayList<InformationData> informationDataArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModule = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        informationDataArrayList = new ArrayList<>();
        initialiseView();
        setUpEvent();
        loadData();
    }

    //initialize id
    void initialiseView() {
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Add row divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //load value in adapter
        informationListAdapter = new InformationListAdapter(this, informationDataArrayList);
        recyclerView.setAdapter(informationListAdapter);
    }

    //receive response and load data
    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        loadInformation();
    }

    //refresh data
    private void setUpEvent() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadInformation();
            }
        });
    }

    private void loadInformation() {
        viewModule.loadInformation().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                swipeRefreshLayout.setRefreshing(false);
                updateResponse(response);
            }
        });
    }

    //receive response from api
    void updateResponse(final Response response) {
        informationDataArrayList.clear();
        if (response.status == Response.SUCCESS) {
            FactData fact = (FactData) response.data;
            if (fact != null) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(fact.getTitle());
                }
                if (fact.getInformationData() != null && fact.getInformationData().size() > 0)
                    informationDataArrayList.addAll(parseInformation(fact.getInformationData()));
            }
        } else {
            Toast.makeText(getApplicationContext(), response.error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        informationListAdapter.notifyDataSetChanged();
    }

    //remove the empty or null value in list
    private ArrayList<InformationData> parseInformation(ArrayList<InformationData> informationData) {
        ArrayList<InformationData> list = new ArrayList<>();
        for (InformationData information : informationData) {
            if (!TextUtils.isEmpty(information.getTitle()) || !TextUtils.isEmpty(information.getDescription()) || !TextUtils.isEmpty(information.getImageUrl())) {
                list.add(list.size(), information);
            }
        }
        return list;
    }
}
