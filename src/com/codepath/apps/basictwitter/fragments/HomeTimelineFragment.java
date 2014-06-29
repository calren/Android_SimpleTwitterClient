package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;
	private String max_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline();
	}

	@Override
    public void populateTimeline() {
		String max_id = "";
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
//				max_id = getItem(tweets.size()-1).getId();
//				lvTweets.onRefreshComplete();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		});
	}

    @Override
	public void populateTimeline(String lastTweetID) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
//				max_id = getItem(tweets.size()-1).getId();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, lastTweetID);
	}

}
