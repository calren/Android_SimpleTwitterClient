package com.codepath.apps.basictwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	private TwitterClient client;
	private EditText tweetMsg;
	private Button submitBtn;
	Context context;
	String post = "";
	String user = "";

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
//		System.out.println("post = " + post);
//		System.out.println("user = " + user);
//		finish();
	}
	
	public void postStatus(String status) {
		client.postStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				try {
					System.out.println(json.getString("text"));
					System.out.println(json.getJSONObject("user").getString("screen_name"));
					System.out.println(json.getJSONObject("user").getString("name"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				System.out.println("lame");
			}
		}, status);
	}
}
