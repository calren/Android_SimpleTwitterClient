package com.codepath.apps.basictwitter;

import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	private static Tweet tweet;

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
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenname);
		TextView tvTimeCreated = (TextView) v.findViewById(R.id.tvTime);
		
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		
		//DateUtils.getRelativeTimeSpanString (long time, long now, long minResolution)
		String date = tweet.getCreatedAt();
//		CharSequence date = DateUtils.getRelativeTimeSpanString(Long.valueOf(tweet.getCreatedAt()).longValue(), 
//				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		
		tvTimeCreated.setText(date);
		
		return v;
	}

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);

	}

}
