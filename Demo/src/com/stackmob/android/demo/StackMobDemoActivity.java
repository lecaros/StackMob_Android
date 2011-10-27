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

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.stackmob.sdk.push.StackMobPushToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import com.stackmob.android.sdk.common.StackMobCommon;
import android.util.Log;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

public class StackMobDemoActivity extends Activity {
	private StackMob stackmob;
	private static final String TAG = StackMobDemoActivity.class.getCanonicalName();
	private final StackMobCallback standardToastCallback = new StackMobCallback() {
		@Override public void success(String responseBody) {
			threadAgnosticToast(StackMobDemoActivity.this, "response: " + responseBody, Toast.LENGTH_SHORT);
		}
		@Override public void failure(StackMobException e) {
			threadAgnosticToast(StackMobDemoActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT);
		}
	};
	
	public StackMobDemoActivity() {
		StackMobCommon.API_KEY = "8bce5b97-6018-4993-a690-4cc034aa2bfe";
		StackMobCommon.API_SECRET = "c2227f24-7ad5-452f-8669-4a4a454c8fe4";
		StackMobCommon.USER_OBJECT_NAME = "user";
		StackMobCommon.API_VERSION = 0;
		stackmob = StackMobCommon.getStackMobInstance();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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

	public void loginClick(View v) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", getUsername());
		params.put("password", getPassword());
		stackmob.login(params, standardToastCallback);
	}
	
	public void createUserClick(View v) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", getUsername());
		params.put("password", getPassword());
		stackmob.post("user", params, standardToastCallback);
	}
	
	public void registerRegTokenClick(View w) {
		try {
			final String username = getPushUsername();
			final String registrationID = getRegistrationIDHolder().getID();
			stackmob.registerForPushWithUser(username, registrationID, standardToastCallback);
		}
		catch(C2DMRegistrationIDHolder.NoStoredRegistrationIDException e) {
			threadAgnosticToast(StackMobDemoActivity.this, "no registration ID currently stored", Toast.LENGTH_SHORT);
		}
		
	}
	
	public void sendPushClick(View w) {
		try {
			final Map<String, String> payload = new HashMap<String, String>();
			payload.put("payload", getPushPayload());
			final List<StackMobPushToken> tokens = new ArrayList<StackMobPushToken>();
			tokens.add(new StackMobPushToken(getRegistrationIDHolder().getID(), StackMobPushToken.TokenType.Android));
			stackmob.pushToTokens(payload, tokens, standardToastCallback);
		}
		catch(C2DMRegistrationIDHolder.NoStoredRegistrationIDException e) {
			threadAgnosticToast(StackMobDemoActivity.this, "no registration ID currently stored", Toast.LENGTH_SHORT);
		}
	}
	
	public void getRegTokenClick(View w) {
		try {
			threadAgnosticToast(StackMobDemoActivity.this, getRegistrationIDHolder().getID(), Toast.LENGTH_SHORT);
		}
		catch(C2DMRegistrationIDHolder.NoStoredRegistrationIDException e) {
			threadAgnosticToast(StackMobDemoActivity.this, "no registration ID currently stored", Toast.LENGTH_SHORT);
		}
	}
	
	private C2DMRegistrationIDHolder getRegistrationIDHolder() {
		return new C2DMRegistrationIDHolder(StackMobDemoActivity.this);
	}
	
	private void registerForC2DM() {
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		intent.putExtra("sender", "aaron@stackmob.com");
		startService(intent);
	}

	private EditText getUsernameField() {
		return (EditText)findViewById(R.id.username);
	}
	
	private EditText getPasswordField() {
		return (EditText)findViewById(R.id.password);
	}
	
	private EditText getPushUsernameField() {
		return (EditText)findViewById(R.id.push_username);
	}
	
	private String getUsername() {
		return getUsernameField().getText().toString();
	}
	
	private String getPushUsername() {
		return getPushUsernameField().getText().toString();
	}
	
	private String getPassword() {
		return getPasswordField().getText().toString();
	}
	
	private EditText getPushPayloadField() {
		return (EditText)findViewById(R.id.token_username);
	}
	
	private String getPushPayload() {
		return getPushPayloadField().getText().toString();
	}
	
	private void threadAgnosticToast(final Context ctx, final String txt, final int duration) {
		runOnUiThread(new Runnable() {
			@Override public void run() {
				Toast.makeText(ctx, txt, duration).show();
			}
		});
	}
}