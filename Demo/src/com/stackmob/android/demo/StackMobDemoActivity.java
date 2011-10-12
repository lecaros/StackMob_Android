package com.stackmob.android.demo;

import java.util.HashMap;

import com.stackmob.android.sdk.api.StackMob;
import com.stackmob.android.sdk.callback.StackMobCallback;
import com.stackmob.android.sdk.exception.StackMobException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StackMobDemoActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    StackMob.getInstance().setApplication(YOUR_API_KEY, YOUR_API_SECRET, YOUR_APP_NAME, SUBDOMAIN, "stackmob.com", USER_OBJECT_NAME, API_VERSION);
  }

  public void buttonClick(View v) {

    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("username", "admin");
    params.put("password", "1234");

    StackMob.getInstance().login(params, new StackMobCallback() {

      @Override
      public void success(String responseBody) {
        Toast.makeText(StackMobDemoActivity.this,
            "login response: " + responseBody, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void failure(StackMobException e) {
        Toast.makeText(StackMobDemoActivity.this,
            "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}