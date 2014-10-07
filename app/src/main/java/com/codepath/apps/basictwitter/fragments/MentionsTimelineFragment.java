package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.applications.TwitterApplication;
import com.codepath.apps.basictwitter.clients.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateMentionsTimeline();
    }

    public void populateMentionsTimeline(){
        client.getMentionsTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {

                    addAll(Tweet.fromJSONArray(jsonArray));
                    stopRefresh();
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
