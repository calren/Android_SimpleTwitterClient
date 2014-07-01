package com.codepath.apps.basictwitter;

import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = UserTimelineFragment.newInstance(getIntent().getStringExtra("user"));
		ft.replace(R.id.flContainer, fragment);
		ft.commit();
		
		System.out.println("user is : " + getIntent().getStringExtra("user"));
		setContentView(R.layout.activity_user_profile);
		
		TwitterClient client = TwitterApplication.getRestClient();

		System.out.println("before json call");
		client.getAccountInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				User user = new User();
				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
				TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
				TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
				TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
				ImageLoader imageLoader = ImageLoader.getInstance();
				try {
					user = User.fromJSON(json.getJSONObject(2).getJSONObject("user"));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				getActionBar().setTitle("@" + user.getScreenName());
				imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
				tvUserName.setText(user.getName());
				tvTagline.setText(user.getTagline());
				tvFollowing.setText(user.getFollowingCount() + " Following");
				tvFollowers.setText(user.getFollowersCount() + " Followers");
			;}
			
			@Override
			public void onFailure(Throwable e, String s) {
				e.printStackTrace();
			}
		}, getIntent().getStringExtra("user") );
		System.out.println("after json call");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public String getUser() {
		return getIntent().getStringExtra("user");
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
