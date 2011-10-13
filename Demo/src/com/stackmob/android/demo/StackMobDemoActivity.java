package com.stackmob.android.demo;

import java.util.HashMap;

import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import static com.stackmob.android.sdk.common.StackMobCommon.*;

public class StackMobDemoActivity extends Activity {
	private StackMob stackmob;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		stackmob = new StackMob("YOUR_API_KEY", "YOUR_API_SECRET", "USER_OBJECT_NAME", 0);
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