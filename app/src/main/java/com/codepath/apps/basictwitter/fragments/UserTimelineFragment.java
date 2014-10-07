package com.codepath.apps.basictwitter.fragments;


import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.applications.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class UserTimelineFragment extends TweetsListFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //populateUserTimeline(null);
    }

    public void populateUserTimeline(User u){
        clearAll();

        TwitterApplication.getRestClient().getUserTimeline(u, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {

                addAll(Tweet.fromJSONArray(jsonArray));
            }

            @Override
            public void onFailure(Throwable e, String string) {
                Log.d("debug", e.toString());
                Log.d("debug", string.toString());
            }
        });
    }


}
