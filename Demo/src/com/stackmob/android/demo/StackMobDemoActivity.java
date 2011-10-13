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

public class StackMobDemoActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		StackMobCommon.API_KEY = "YOUR_API_KEY";
		StackMobCommon.API_SECRET = "YOUR_API_SECRET";
		StackMobCommon.USER_OBJECT_NAME = "YOUR_USER_OBJECT_NAME";
		StackMobCommon.API_VERSION = 0;
	}

	public void buttonClick(View v) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("username", "admin");
		params.put("password", "1234");
		
		StackMobCommon.getStackMobInstance().login(params, new StackMobCallback() {
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