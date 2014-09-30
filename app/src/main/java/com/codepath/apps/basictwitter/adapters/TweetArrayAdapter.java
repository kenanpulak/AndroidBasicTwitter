package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
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
        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        ImageLoader imageLoader = ImageLoader.getInstance();
        // Populate views with tweet data
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(),ivProfileImage);
        Typeface gothamMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gotham-Medium.otf");
        tvUserName.setTypeface(gothamMedium);
        tvUserName.setText(tweet.getUser().getName());

        Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gotham-Book.otf");
        tvScreenName.setTypeface(gothamBook);
        tvScreenName.setText("@" + tweet.getUser().getScreenName());

        tvTime.setTypeface(gothamBook);
        tvTime.setText(Tweet.getRelativeTimeAgo(tweet.getCreatedAt()));

        tvBody.setText(tweet.getBody());
        tvBody.setTypeface(gothamBook);

        return view;
    }
}
