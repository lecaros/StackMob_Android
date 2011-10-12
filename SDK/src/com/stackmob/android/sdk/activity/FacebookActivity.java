package com.stackmob.android.sdk.activity;

import oauth.signpost.OAuth;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.stackmob.android.sdk.api.StackMob;
import com.stackmob.android.sdk.api.StackMobSession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FacebookActivity extends Activity {

  private static final String TAG = FacebookActivity.class.getCanonicalName();

  public static final int RESULT_ERROR = 0;
  public static final int RESULT_OK = 1;

  private Facebook facebook;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StackMobSession session = StackMob.getInstance().getSession();

    facebook = new Facebook(session.getFacebookAppId());
    facebook.authorize(this, new DialogListener() {

      public void onComplete(Bundle values) {

        StackMob.getInstance().setFacebookToken(facebook.getAccessToken(),
            facebook.getAccessExpires(), null);
        finish();
      }

      public void onFacebookError(FacebookError error) {
        StackMob.getInstance().setFacebookToken(null, 0, error);
        finish();
      }

      public void onError(DialogError e) {
        StackMob.getInstance().setFacebookToken(null, 0, e);
        finish();
      }

      public void onCancel() {
        StackMob.getInstance().setFacebookToken(null, 0, new Exception("Cancelled"));
        finish();
      }

    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent data) {

    super.onActivityResult(requestCode, resultCode, data);
    facebook.authorizeCallback(requestCode, resultCode, data);
  }
}
