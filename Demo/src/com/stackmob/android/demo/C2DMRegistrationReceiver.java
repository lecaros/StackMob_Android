package com.stackmob.android.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class C2DMRegistrationReceiver extends BroadcastReceiver {
	private static final String TAG = C2DMRegistrationReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
			final String registrationId = intent.getStringExtra("registration_id");
			final String error = intent.getStringExtra("error");
			Log.i(TAG, "Received registration ID " + registrationId + " with error " + error);
			//in your production app, send this registration ID to StackMob for storage using the
			//register_device_token_universal API call
		}
	}
}
