package com.stackmob.android.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class C2DMPayloadReceiver extends BroadcastReceiver {
	private static final String TAG = C2DMPayloadReceiver.class.getCanonicalName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			final String payload = intent.getStringExtra("payload");
			Log.d(TAG, "Got C2dM Payload: " + payload);
		}
	}

}
