package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.text.format.DateUtils;

public class Tweet implements Serializable{
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private String id;
	
	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			try {
				tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
			tweet.id = jsonObject.getString("id");
		} catch (JSONException e ) {
			e.printStackTrace();
			return null;
		}
		
		
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}
	
	public String getId() {
		return id;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJSON(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
				}
			}
		
		return tweets;
	}
	
	public String getTimeCreated() {
		return createdAt;
	}
	
	public static String getRelativeTimeAgo(String rawJsonDate) throws java.text.ParseException {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	@Override
	public String toString() {
		return getBody() + "  - " + getUser().getScreenName();
	}
}
