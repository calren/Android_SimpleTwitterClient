package com.codepath.apps.basictwitter;

import org.json.JSONObject;

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

public class UserProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = UserTimelineFragment.newInstance(getIntent().getStringExtra("user"));
		ft.replace(R.id.fragmentUserTimeline, fragment);
		ft.commit();
		
		setContentView(R.layout.activity_user_profile);
		
		TwitterClient client = TwitterApplication.getRestClient();

		client.getAccountInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
//				TextView tvScreenName = (TextView) findViewById(R.id.tvScreenname);
//				System.out.println("loaded");
				ImageLoader imageLoader = ImageLoader.getInstance();
				User user = User.fromJSON(json);
				getActionBar().setTitle("@" + user.getScreenName());
//
				imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
				tvUserName.setText(user.getName());
//				tvScreenName.setText("@" + user.getScreenName());
			;}
			
			@Override
			public void onFailure(Throwable e, String s) {
				e.printStackTrace();
			}
		},getIntent().getStringExtra("user"));
		
		System.out.println("user: " + getIntent().getStringExtra("user"));
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
