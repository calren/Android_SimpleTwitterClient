package com.codepath.apps.basictwitter;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	
	private String max_id;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);


//		lvTweets.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				aTweets.notifyDataSetChanged();
//			}
//		});
		
//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list contents
//                // Make sure you call listView.onRefreshComplete()
//                // once the loading is done. This can be done from here or any
//                // place such as when the network request has completed successfully.
//                fetchTimelineAsync(0);
//            }
//        });
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_compose:
                openCompose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void openCompose() {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, 100);
    }
    
//    @Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	if (resultCode == RESULT_OK) {
//    		Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
//    		aTweets.insert(tweet, 0);
//    	    aTweets.notifyDataSetChanged();
//    	    lvTweets.setSelection(0);
//    	}
//	}

}
