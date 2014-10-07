package com.codepath.apps.basictwitter.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.clients.TwitterClient;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetFragment;
import com.codepath.apps.basictwitter.listeners.SupportFragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity implements TweetFragment.TweetFragmentListener{

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupTabs();
        }

    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(new SupportFragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this,
                        "home", HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(new SupportFragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this,
                        "mentions", MentionsTimelineFragment.class));
        actionBar.addTab(tab2);
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
            showTweetFragment();
            return true;
        }
        if (id == R.id.action_profile){
            showProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProfile(){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
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
                //fragmentTweetsList.insertTweetAtIndex(tweet, 0);
            }

            @Override
            public void onFailure(Throwable e, String string) {

                progress.hide();
            }
        });
    }
}
