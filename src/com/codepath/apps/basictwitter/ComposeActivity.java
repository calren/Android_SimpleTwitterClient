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
		Intent data = new Intent();

		tweetMsg = (EditText) findViewById(R.id.etTweet);
		
		String name = postStatus(tweetMsg.getText().toString());
		System.out.println("name = " + name);
		
		data.putExtra("tweet", "");
		finish();

	}
	
	String name = "";
	public String postStatus(String status) {

		client.postStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				String a = "";
				try {
					name = json.getJSONObject("user").getString("name");
					System.out.println(json.getJSONObject("user").getString("name"));
					newestTweet = json;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newestTweet = json;
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				System.out.println("lame");
			}
		}, status);
		
		return name;
		
	}
}
