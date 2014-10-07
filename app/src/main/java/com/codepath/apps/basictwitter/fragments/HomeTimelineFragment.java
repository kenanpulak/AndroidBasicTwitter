package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.applications.TwitterApplication;
import com.codepath.apps.basictwitter.clients.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
    }

    public void populateTimeline(final long max){
        client.getHomeTimeline(max,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (max != 0){
                    ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
                    arrayList.remove(0);
                    addAll(arrayList);
                    stopRefresh();
                }
                else {
                    addAll(Tweet.fromJSONArray(jsonArray));
                    stopRefresh();
                }
            }

            @Override
            public void onFailure(Throwable e, String string) {
                stopRefresh();
                Log.d("debug", e.toString());
                Log.d("debug",string.toString());
            }
        });
    }

}
