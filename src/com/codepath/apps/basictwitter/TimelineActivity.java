package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.*;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	
	private PullToRefreshListView lvTweets;
	private String max_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
	}
	
	public void fetchTimelineAsync(int page) {
        populateTimeline();
    }
	
	public void customLoadMoreDataFromApi(int offset) {
	      // This method probably sends out a network request and appends new data items to your adapter. 
	      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
	      // Deserialize API response and then construct new objects to append to the adapter
		populateTimeline(max_id);
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
				max_id = aTweets.getItem(tweets.size()-1).getId();
				lvTweets.onRefreshComplete();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		});
	}
	
	public void populateTimeline(String lastTweetID) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
				max_id = aTweets.getItem(tweets.size()-1).getId();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, lastTweetID);
	}
	
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_compose:
                openCompose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void openCompose() {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, 100);
    }
}
