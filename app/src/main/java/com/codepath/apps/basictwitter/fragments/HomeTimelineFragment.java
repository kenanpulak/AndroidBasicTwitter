package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.applications.TwitterApplication;
import com.codepath.apps.basictwitter.clients.TwitterClient;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearAll();
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(true);
                populateTimeline(0);
            }
        });

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Tweet tweet= getItemAtIndex(totalItemsCount-1);
                populateTimeline(tweet.getUid());
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
        return view;
    }

    public void populateTimeline(final long max){
        client.getHomeTimeline(max,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (max != 0){
                    ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
                    arrayList.remove(0);
                    addAll(arrayList);
                    swipeContainer.setRefreshing(false);
                }
                else {
                    addAll(Tweet.fromJSONArray(jsonArray));
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Throwable e, String string) {
                swipeContainer.setRefreshing(false);

                Log.d("debug", e.toString());
                Log.d("debug",string.toString());
            }
        });
    }


}
