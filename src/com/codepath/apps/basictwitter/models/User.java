package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.text.format.DateUtils;

public class User implements Serializable{
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String timeCreated;

	public static User fromJSON(JSONObject jsonObject){
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
