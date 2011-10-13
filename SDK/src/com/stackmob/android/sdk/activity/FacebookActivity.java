package com.stackmob.android.sdk.activity;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.stackmob.sdk.exception.StackMobException;

import static com.stackmob.android.sdk.common.StackMobCommon.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FacebookActivity extends Activity {
	public static final int RESULT_ERROR = 0;
	public static final int RESULT_OK = 1;
	
	private Facebook facebook;

	private void setFacebookToken(String accessToken, long accessTokenExpiry, Throwable e) {
		if(FacebookCallback != null) {
			if(e != null) {
				FacebookCallback.failure(new StackMobException(e.getMessage()));
			}
			else {
				FacebookCallback.success(accessToken, accessTokenExpiry);
			}
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		facebook = new Facebook(FACEBOOK_APP_ID);
		facebook.authorize(this, new DialogListener() {			
			@Override			
			public void onComplete(Bundle values) {
				setFacebookToken(facebook.getAccessToken(), facebook.getAccessExpires(), null);
				finish();
			}
			
			@Override
			public void onFacebookError(FacebookError error) {
				setFacebookToken(null, 0, error);
				finish();
			}
			
			@Override
			public void onError(DialogError e) {
				setFacebookToken(null, 0, e);
				finish();
			}
			
			@Override
			public void onCancel() {
				setFacebookToken(null, 0, new Exception("Cancelled"));
				finish();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}