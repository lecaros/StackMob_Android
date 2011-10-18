package com.stackmob.android.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.stackmob.android.sdk.common.StackMobCommon;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;


public class C2DMRegistrationReceiver extends BroadcastReceiver {
	private static final String TAG = C2DMRegistrationReceiver.class.getCanonicalName();
	private static final String USERNAME = "TEST_USER";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
			final String registrationId = intent.getStringExtra("registration_id");
			final String error = intent.getStringExtra("error");
			Log.i(TAG, "Received registration ID " + registrationId + " with error " + error);
			
			//send the registrationID to StackMob
			String receiver = context.getPackageName() + ".C2DMRegistrationIDSender";
			intent.setClassName(context, receiver);
			intent.putExtra("registrationID", registrationId);
			intent.putExtra("username", USERNAME);
			context.startService(intent);
		}
	}
}
