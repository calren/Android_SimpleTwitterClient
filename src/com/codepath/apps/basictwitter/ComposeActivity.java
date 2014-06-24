package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	
	private TwitterClient client;
	private EditText tweetMsg;
	Context context;

	JSONObject newestTweet;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		client = TwitterApplication.getRestClient();

		client.getAccountInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
				TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
				TextView tvScreenName = (TextView) findViewById(R.id.tvScreenname);
				System.out.println("loaded");
				ImageLoader imageLoader = ImageLoader.getInstance();
				User user = User.fromJSON(json);


				imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
				tvUserName.setText(user.getName());
				tvScreenName.setText("@" + user.getScreenName());
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				e.printStackTrace();
			}
		});

		
		tweetMsg = (EditText) findViewById(R.id.etTweet);
		
		context = getApplicationContext();
		setContentView(R.layout.activity_compose);
	}
	
	public void submitTweet() {

		tweetMsg = (EditText) findViewById(R.id.etTweet);
		
		postStatus(tweetMsg.getText().toString());
	}
	
	public void postStatus(String status) {

		client.postStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Intent data = new Intent();	
				Tweet tweet = Tweet.fromJSON(json);
				data.putExtra("tweet", tweet);
				data.putExtra("name", "ThisismyName");
				setResult(RESULT_OK, data);
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				e.printStackTrace();
			}
		}, status);
				
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.compose, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_send:
                submitTweet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
