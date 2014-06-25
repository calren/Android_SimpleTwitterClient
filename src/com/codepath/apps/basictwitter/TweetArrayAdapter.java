package com.codepath.apps.basictwitter;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	private static Tweet tweet;
	private TwitterClient client;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		tweet = getItem(position);
		client = TwitterApplication.getRestClient();

		View v;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenname);
		TextView tvTimeCreated = (TextView) v.findViewById(R.id.tvTime);
		
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		
		ImageButton ibAction = (ImageButton) v.findViewById(R.id.imgAction);
		if (tweet.getUser().getScreenName().equals("calren24")) {
			ibAction.setImageResource(R.drawable.ic_action_trash);
		}
		
//		client.getAccountInfo(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				System.out.println("WOO");
//				User user = User.fromJSON(json);
////				System.out.println(user.getScreenName());
//				if (tweet.getUser().getScreenName().equals(user.getScreenName())) {
//					System.out.println("found it found it ");
//					ibAction.setImageResource(R.drawable.ic_action_trash);
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable e, String s) {
//				System.out.println("BOO");
//				e.printStackTrace();
//			}
//		});
		

		String date = tweet.getCreatedAt().replace(" ago", "");
		date = date.replace(" minutes", "m");
		date = date.replace(" hours", "h");
		date = date.replace(" days", "d");
		if (date.contains("seconds")) {
			date = "Just now";
		}
		
		tvTimeCreated.setText(date);
		
		return v;
	}

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

}
