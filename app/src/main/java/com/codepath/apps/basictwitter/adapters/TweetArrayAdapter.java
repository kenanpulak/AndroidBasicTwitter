package com.codepath.apps.basictwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by kenanpulak on 9/26/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    // View lookup cache
    private static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTime;
        ImageLoader imageLoader;
    }

    public TweetArrayAdapter(Context context, List<Tweet> tweets){
        super(context,0,tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for position
        final Tweet tweet = getItem(position);
        // Find or inflate the template
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tweet_item,parent,false);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.imageLoader = ImageLoader.getInstance();
            Typeface gothamMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gotham-Medium.otf");
            Typeface gothamBook = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gotham-Book.otf");

            viewHolder.tvBody.setTypeface(gothamBook);
            viewHolder.tvScreenName.setTypeface(gothamBook);
            viewHolder.tvUserName.setTypeface(gothamMedium);
            viewHolder.tvTime.setTypeface(gothamBook);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate views with tweet data
        viewHolder.imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), viewHolder.ivProfileImage);
        viewHolder.tvUserName.setText(tweet.getUser().getName());

        viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfileActivity.class);
                intent.putExtra("user", tweet.getUser());
                v.getContext().startActivity(intent);
            }
        });

            viewHolder.tvScreenName.setText("@"+tweet.getUser().

            getScreenName()

            );

            viewHolder.tvTime.setText(Tweet.getRelativeTimeAgo(tweet.getCreatedAt()));

            viewHolder.tvBody.setText(tweet.getBody());

            return convertView;
        }
    }
