package com.codepath.apps.basictwitter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
        populateTimeline();
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
                populateTimeline();
            }
        });
        }

    public void populateTimeline(){
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONArray jsonArray) {
                aTweets.addAll(Tweet.fromJSONArray(jsonArray));
                swipeContainer.setRefreshing(false);
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
                aTweets.insert(tweet,0);
            }

            @Override
            public void onFailure(Throwable e, String string) {

                progress.hide();
            }
        });
    }
}
