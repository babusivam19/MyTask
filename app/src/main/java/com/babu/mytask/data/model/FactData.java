package com.babu.mytask.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Babu on 7/4/2018.
 */
public class FactData {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private ArrayList<InformationData> informationData;

    public FactData(String title, ArrayList<InformationData> informationData) {
        this.title = title;
        this.informationData = informationData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<InformationData> getInformationData() {
        return informationData;
    }

    public void setInformationData(ArrayList<InformationData> informationData) {
        this.informationData = informationData;
    }


}
