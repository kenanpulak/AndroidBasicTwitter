package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kenanpulak on 9/26/14.
 */
public class User implements Serializable{

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followersCount;
    private int followingCount;
    private int tweetsCount;

    // User.fromJSON(...)
    public static User fromJSON(JSONObject jsonObject){

        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.followersCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
            u.tweetsCount = jsonObject.getInt("statuses_count");
            u.tagline = jsonObject.getString("description");

        }catch (JSONException e){
            e.printStackTrace();
        }

        return u;
    }

    public String getName(){
        return name;
    }

    public long getUid(){
        return uid;
    }

    public String getScreenName(){
        return screenName;
    }

    public String getProfileImageUrl(){
        return profileImageUrl;
    }

    public int getFollowersCount() { return followersCount; }

    public int getFollowingCount() { return followingCount; }

    public int getTweetsCount() { return tweetsCount; }

    public String getTagline() { return tagline; }


}
