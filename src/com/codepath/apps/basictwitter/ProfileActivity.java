package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		TwitterClient client = TwitterApplication.getRestClient();

		client.getAccountInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
//				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
//				TextView tvScreenName = (TextView) findViewById(R.id.tvScreenname);
//				System.out.println("loaded");
//				ImageLoader imageLoader = ImageLoader.getInstance();
				User user = User.fromJSON(json);
				getActionBar().setTitle("@" + user.getScreenName());
//
//				imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
//				tvUserName.setText(user.getName());
//				tvScreenName.setText("@" + user.getScreenName());
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
