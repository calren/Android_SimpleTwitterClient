package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	
	private ListView lvTweets;
	private String max_id;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.fragment_timeline);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
//		        HomeTimelineFragment.customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
		
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);

	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	

}
