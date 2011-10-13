package com.stackmob.android.sdk.common;

import com.stackmob.android.sdk.callback.*;
import com.stackmob.sdk.api.StackMob;

public class StackMobCommon {
	public static String API_KEY = "YOUR_API_KEY_HERE";
	public static String API_SECRET = "YOUR_API_SECRET_HERE";
	public static String USER_OBJECT_NAME = "YOUR_USER_OBJECT_NAME_HERE";
	public static Integer API_VERSION = 0;
	
	public static String TWITTER_CONSUMER_KEY = "YOUR_TWITTER_CONSUMER_KEY_HERE";
	public static String TWITTER_CONSUMER_SECRET = "YOUR_TWITTER_CONSUMER_SECRET_HERE";
	
	public static String FACEBOOK_APP_ID = "YOUR_FACEBOOK_APP_ID_HERE";
	
	private static StackMob stackmob;
	
	public static StackMob getStackMobInstance() {
		if(stackmob == null) {
			stackmob = new StackMob(API_KEY, API_SECRET, USER_OBJECT_NAME, API_VERSION);
		}
		return stackmob;
	}
	
	public static StackMobTwitterCallback TwitterCallback = null;
	public static StackMobFacebookCallback FacebookCallback = null;
}
