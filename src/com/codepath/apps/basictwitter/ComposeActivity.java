package com.codepath.apps.basictwitter;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	private TwitterClient client;
	private EditText tweetMsg;
	private Button submitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tweetMsg = (EditText) findViewById(R.id.etTweet);
		submitBtn = (Button) findViewById(R.id.btnSubmit);
		
		client = TwitterApplication.getRestClient();
		
		setContentView(R.layout.activity_compose);
	}
	
	public void submitTweet(View v) {
		tweetMsg = (EditText) findViewById(R.id.etTweet);
		System.out.println(tweetMsg.getText().toString());
		final Context context = getApplicationContext();
		client.postStatus(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray json) {
				Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
			}
		}, tweetMsg.getText().toString());
		finish();
	}
}
