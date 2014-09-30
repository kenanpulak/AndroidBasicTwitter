package com.codepath.apps.basictwitter.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.applications.TwitterApplication;
import com.codepath.apps.basictwitter.clients.TwitterClient;
import com.codepath.apps.basictwitter.fragments.TweetFragment;
import com.codepath.apps.basictwitter.listeners.EndlessScrollListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends FragmentActivity implements TweetFragment.TweetFragmentListener{

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aTweets.clear();
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(0);
            }
        });

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Tweet tweet= aTweets.getItem(totalItemsCount-1);
                populateTimeline(tweet.getUid());
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
        }

    public void populateTimeline(final long max){
        client.getHomeTimeline(max,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (max != 0){
                    ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
                    arrayList.remove(0);
                    aTweets.addAll(arrayList);
                    swipeContainer.setRefreshing(false);
                }
                else {
                    aTweets.addAll(Tweet.fromJSONArray(jsonArray));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_compose) {
            //Toast.makeText(this,"Compose Tapped",Toast.LENGTH_SHORT).show();
            showTweetFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTweetFragment(){

            FragmentManager fm = getSupportFragmentManager();
            TweetFragment filterFragment = TweetFragment.newInstance("New Tweet");
            filterFragment.show(fm, "fragment_tweet");

    }

    @Override
    public void onFinishTweetFragment(String message){

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        client.postTweet(message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {

                Tweet tweet = Tweet.fromJson(jsonObject);
                progress.hide();
                Log.d("Tweet posted",tweet.getBody());
                aTweets.insert(tweet, 0);
            }

            @Override
            public void onFailure(Throwable e, String string) {

                progress.hide();
            }
        });
    }
}
