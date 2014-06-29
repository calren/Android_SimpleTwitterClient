package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.UserProfileActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class OtherUsersTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;
	String user;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		String user = getArguments().getString("user", "");	
		System.out.println(user);
		populateTimeline();
		
//		UserProfileActivity activity = (UserProfileActivity) getActivity();
//		System.out.println(activity.getUser());
	}
	
	public static OtherUsersTimelineFragment newInstance(String user) {
		OtherUsersTimelineFragment fragment = new OtherUsersTimelineFragment();
        Bundle args = new Bundle();
        args.putString("user", "blah");
        fragment.setArguments(args);
        return fragment;
    }

	@Override
    public void populateTimeline() {
		client.getUserTimeline(new JsonHttpResponseHandler() {

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
		client.getUserTimeline(new JsonHttpResponseHandler() {

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
