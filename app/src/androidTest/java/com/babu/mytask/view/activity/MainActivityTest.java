package com.babu.mytask.view.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.babu.mytask.data.model.FactData;
import com.babu.mytask.data.model.InformationData;
import com.babu.mytask.data.model.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Babu on 7/7/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class);
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void allDataLoadedSuccessfully() {
        ArrayList<InformationData> informationData = new ArrayList<>();
        informationData.add(new InformationData("Title 1", "Desc 1", ""));
        informationData.add(new InformationData("Title 2", "Desc 2", ""));
        FactData data = new FactData("My Title", informationData);

        activity.updateResponse(Response.success(data));
        assertEquals(activity.getSupportActionBar().getTitle(), data.getTitle());
        assertEquals(activity.informationListAdapter.getItemCount(), 2);
    }

    @Test
    public void dataLoadingFailed() {
        activity.updateResponse(Response.error(null, new Throwable("Something went wrong")));
        assertEquals(activity.informationListAdapter.getItemCount(), 0);
    }
}