package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

import java.util.ArrayList;

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    public ListView lvTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Non-View Initialization
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_tweets_list,container,false);
        // Assign our view references
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        // Return the layout view
        return view;
    }

    public void addAll(ArrayList<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    public void addTweet(Tweet tweet){
        aTweets.add(tweet);
    }

    public void insertTweetAtIndex(Tweet tweet, int index){
        aTweets.insert(tweet,index);
    }

    public void clearAll() {aTweets.clear();}

    public Tweet getItemAtIndex(int index){ return aTweets.getItem(index); }


}
