package com.codepath.apps.basictwitter.clients;

import android.content.Context;

import com.codepath.apps.basictwitter.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/";
	public static final String REST_CONSUMER_KEY = "u4MQGLfBd21R0KO7hby89uysE";
	public static final String REST_CONSUMER_SECRET = "I6lNMgLEdiP7OsNJePvUf7GdofdG6TDToqPoGt9qIZ6JRCrOSg";
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getHomeTimeline(long uid, AsyncHttpResponseHandler handler){

        String apiURL = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();

        if (uid != 0){
            params.put("max_id",String.valueOf(uid));
        }
        params.put("since_id","1");
        params.put("count","200");
        client.get(apiURL,params,handler);

    }

    public void postTweet(String message, AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status",message);
        client.post(apiURL,params,handler);

    }

    public void getMentionsTimeline(JsonHttpResponseHandler handler){
        String apiURL = getApiUrl("statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count","200");
        client.get(apiURL,params,handler);
    }

    public void getMyInfo(AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl("account/verify_credentials.json");
        client.get(apiURL,null,handler);
    }

    public void getUserInfo(User u, AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", String.valueOf(u.getUid()));
        client.get(apiURL,params,handler);
    }

    public void getUserTimeline(User user, AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl("statuses/user_timeline.json");

        if (user != null){
            RequestParams params = new RequestParams();
            params.put("user_id", String.valueOf(user.getUid()));
            client.get(apiURL, params, handler);
        }
        else {
            client.get(apiURL, null, handler);
        }
    }

}