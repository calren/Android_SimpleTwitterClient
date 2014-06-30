package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;
	String user = "";
	String max_id = "";
	int page = 1;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		user = getArguments().getString("user", "");
		System.out.println(user);
		
		client = TwitterApplication.getRestClient();
		populateTimeline("blah");
	}
	
	public static UserTimelineFragment newInstance(String user) {
		UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("user", user);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
	public void populateTimeline(String lastTweetID) {
		client.getUserTimelinePage(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
//				max_id = getItem(tweets.size()-1).getId();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, user, Integer.toString(page));
		
		page++;
	}

}
