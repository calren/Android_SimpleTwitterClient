package com.codepath.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	private static Tweet tweet;
	private ImageButton ibStar;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		tweet = getItem(position);
		View v;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
				
		final ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenname);
		TextView tvTimeCreated = (TextView) v.findViewById(R.id.tvTime);
		
		ivProfileImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), UserProfileActivity.class);
				i.putExtra("user", ivProfileImage.getTag().toString()); 
				getContext().startActivity(i);
			}
		});
		
		ibStar = (ImageButton) v.findViewById(R.id.btnStar);
		
		ibStar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ibStar.setImageResource(R.drawable.ic_action_star);
			}
		});
		
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvScreenName.setText("@" + tweet.getUser().getScreenName());

		String date = tweet.getCreatedAt().replace(" ago", "");
		date = date.replace(" minutes", "m");
		date = date.replace(" minute", "m");
		date = date.replace(" hours", "h");
		date = date.replace(" hour", "h");
		date = date.replace(" days", "d");
		date = date.replace(" day", "d");
		if (date.contains("second")) {
			date = "Just now";
		}
		
		tvTimeCreated.setText(date);
		
		return v;
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);

	}

}
