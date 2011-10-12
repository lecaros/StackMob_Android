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

package com.stackmob.android.sdk.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.stackmob.android.sdk.api.StackMob;
import com.stackmob.android.sdk.callback.StackMobTwitterCallback;
import com.stackmob.android.sdk.exception.StackMobException;

public class TwitterDemoActivity extends Activity {
  private static final String TAG = TwitterDemoActivity.class.getCanonicalName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StackMob stackmob = StackMob.getInstance();
    stackmob.setApplication("7f1aebc7-0fb8-4265-bfea-2c42c08a3bf0",
        "81573b21-b948-4339-baa3-dbffe0ca4503", "androidtest",
        "fithsaring.mob1", "stackmob.com", "user", 0);

    stackmob.setTwitterConsumer("bbiGY2O3x0Q1wPh7JMgykA",
        "ffNMJzRbFlUCr13rqLkfqz5ePmDHRseNbAxkkyNr2s");

    stackmob.getTwitterUserToken(this, new StackMobTwitterCallback() {

      public void success(String token, String tokenSecret) {
        Log.d(TAG, "sucess! token: " + token + " tokenSecret: "
            + tokenSecret);
      }

      public void failure(StackMobException e) {
        Log.d(TAG, "failure: " + e.getMessage());
      }

    });
  }

}
