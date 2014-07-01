package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
		
		TwitterClient client = TwitterApplication.getRestClient();

		client.getAccountInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
				TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
				TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
				TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
				ImageLoader imageLoader = ImageLoader.getInstance();
				
				User user = User.fromJSON(json);
				getActionBar().setTitle("@" + user.getScreenName());
				imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
				tvUserName.setText(user.getName());
				tvTagline.setText(user.getTagline());
				tvFollowing.setText(user.getFollowingCount() + " Following");
				tvFollowers.setText(user.getFollowersCount() + " Followers");
				
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				UserTimelineFragment fragment = UserTimelineFragment.newInstance(user.getScreenName());
				ft.replace(R.id.flContainer, fragment);
				ft.commit();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
