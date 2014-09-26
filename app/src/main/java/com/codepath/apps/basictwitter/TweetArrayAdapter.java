package com.codepath.apps.basictwitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by kenanpulak on 9/26/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(Context context, List<Tweet> tweets){
        super(context,0,tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for position
        Tweet tweet = getItem(position);
        // Find or inflate the template
        View view;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.tweet_item,parent,false);
        }else {
            view = convertView;
        }

        // Find the views within template
        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        ImageLoader imageLoader = ImageLoader.getInstance();
        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(),ivProfileImage);
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        return view;
    }
}
