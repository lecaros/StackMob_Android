package com.stackmob.android.demo;

import com.stackmob.android.sdk.common.StackMobCommon;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;


public class C2DMRegistrationIDSender extends IntentService {
	private static final String TAG = C2DMRegistrationIDSender.class.getCanonicalName();
	private final StackMob stackmob = StackMobCommon.getStackMobInstance(); 
	public C2DMRegistrationIDSender() {
		super(TAG);
		Log.i(TAG, "started");
	}
	
	@Override public final void onHandleIntent(Intent intent) {
		//TODO: acquire a wake lock when calling startService, and release it when done with this work
		//TODO: handle failures to register
		
		final String username = intent.getStringExtra("username");
		final String registrationID = intent.getStringExtra("registrationID");
		stackmob.registerForPushWithUser(username, registrationID, new StackMobCallback() {
			@Override public void success(String responseBody) {
				Log.i(TAG, "successfully registered " + registrationID + " for user " + username);
			}
			@Override public void failure(StackMobException e) {
				Log.w(TAG, "failed to register " + registrationID + " for user " + username + ": " + e.getMessage());
			}
		});
	}
}
