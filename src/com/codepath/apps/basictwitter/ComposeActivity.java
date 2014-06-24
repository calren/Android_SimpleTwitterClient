package com.codepath.apps.basictwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	private TwitterClient client;
	private EditText tweetMsg;
	private Button submitBtn;
	Context context;

	JSONObject newestTweet;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tweetMsg = (EditText) findViewById(R.id.etTweet);
		submitBtn = (Button) findViewById(R.id.btnSubmit);
		
		client = TwitterApplication.getRestClient();
		context = getApplicationContext();
		setContentView(R.layout.activity_compose);
	}
	
	public void submitTweet(View v) {

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
}
