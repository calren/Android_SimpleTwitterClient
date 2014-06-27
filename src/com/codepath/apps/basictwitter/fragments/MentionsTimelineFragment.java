package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {

	private TwitterClient client;
	private String max_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline();
	}
	
	   public void populateTimeline() {
			client.getMentionsTimeline(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(JSONArray json) {
					addAll(Tweet.fromJSONArray(json));
//					max_id = getItem(tweets.size()-1).getId();
//					lvTweets.onRefreshComplete();
				}

				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
				}
			});
		}

//		public void populateTimeline(String lastTweetID) {
//			client.getMentionsTimeline(new JsonHttpResponseHandler() {
//
//				@Override
//				public void onSuccess(JSONArray json) {
//					addAll(Tweet.fromJSONArray(json));
////					max_id = getItem(tweets.size()-1).getId();
//				}
//
//				@Override
//				public void onFailure(Throwable e, String s) {
//					Log.d("debug", e.toString());
//				}
//			}, lastTweetID);
//		}
	    
		public void customLoadMoreDataFromApi(int offset) {
		      // This method probably sends out a network request and appends new data items to your adapter. 
		      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		      // Deserialize API response and then construct new objects to append to the adapter
//			populateTimeline(max_id);
		}
		
		public void fetchTimelineAsync(int page) {
	      populateTimeline();
	  }
}
