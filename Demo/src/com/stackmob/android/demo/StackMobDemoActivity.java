/**
 * Copyright 2011 StackMob
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stackmob.android.demo;

import java.util.HashMap;

import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.stackmob.android.sdk.common.StackMobCommon;
import android.util.Log;
import android.app.PendingIntent;
import android.content.Intent;

public class StackMobDemoActivity extends Activity {
	private StackMob stackmob;
	private static final String TAG = StackMobDemoActivity.class.getCanonicalName();
	private static final String registrationIDKey = "registrationID";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stackmob = StackMobCommon.getStackMobInstance();
		setContentView(R.layout.main);
		StackMobCommon.API_KEY = "YOUR_API_KEY";
		StackMobCommon.API_SECRET = "YOUR_API_SECRET";
		StackMobCommon.USER_OBJECT_NAME = "YOUR_USER_OBJECT_NAME";
		StackMobCommon.API_VERSION = 0;
		C2DMRegistrationIDHolder regHolder = new C2DMRegistrationIDHolder(this);
		if(regHolder.hasID()) {
			try {
				Log.i(TAG, "registration ID " + regHolder.getID() + " was already stored in shared prefs");
			}
			catch (C2DMRegistrationIDHolder.NoStoredRegistrationIDException e) {
				Log.e(TAG, "failed to get registration ID from shared prefs, even though shared prefs reports that it's there" , e);
			}
		}
		else {
			Log.i(TAG, "registration ID was not already stored in shared prefs. fetching a new one and saving it");
			registerForC2DM();
		}		
	}
	
	private void registerForC2DM() {
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		intent.putExtra("sender", "aaron@stackmob.com");
		startService(intent);
	}
	
	public void buttonClick(View v) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("username", "admin");
		params.put("password", "1234");
		
		stackmob.login(params, new StackMobCallback() {
			@Override
			public void success(String responseBody) {
				Toast.makeText(StackMobDemoActivity.this, "login response: " + responseBody, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void failure(StackMobException e) {
				Toast.makeText(StackMobDemoActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}