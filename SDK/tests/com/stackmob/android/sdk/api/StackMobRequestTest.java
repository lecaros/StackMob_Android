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

package com.stackmob.android.sdk.api;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import com.stackmob.android.sdk.callback.StackMobCallback;
import com.stackmob.android.sdk.exception.StackMobException;
import com.stackmob.android.sdk.net.HttpVerb;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import static StackMobTestCommon.*;

@RunWith(RobolectricTestRunner.class)
public class StackMobRequestTest {

  @BeforeClass
  public static void onlyOnce() {
    StackMob stackmob = StackMob.getInstance();
    stackmob.setApplication(API_KEY, API_SECRET, APP_NAME, SUBDOMAIN, DOMAIN, USER_OBJECT_NAME, API_VERSION);
  }

  @Test
  public void testListapiSecureGetRequest() {

    StackMobRequest request = new StackMobRequest(StackMob
        .getInstance().getSession());

    request.methodName = "listapi";
    request.isSecure = true;
    request.isUserBased = false;
    request.httpMethod = HttpVerb.GET;
    request.callback = new StackMobCallback() {
      
      public void success(String responseBody) {
        assertNotNull(responseBody);
      }
      
      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    request.sendRequest();
  }

  @Test
  public void testListapiSecurePostRequest() {

    StackMobRequest request = new StackMobRequest(StackMob
        .getInstance().getSession());

    request.methodName = "listapi";
    request.isSecure = true;
    request.isUserBased = false;
    request.httpMethod = HttpVerb.POST;
    request.callback = new StackMobCallback() {
      
      public void success(String responseBody) {
        assertNotNull(responseBody);
      }
      
      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    request.sendRequest();
  }

  @Test
  public void testListapiRegularGetRequest() {

    StackMobRequest request = new StackMobRequest(StackMob
        .getInstance().getSession());

    request.methodName = "listapi";
    request.isSecure = false;
    request.isUserBased = false;
    request.httpMethod = HttpVerb.GET;
    request.callback = new StackMobCallback() {
      
      public void success(String responseBody) {
        assertNotNull(responseBody);
      }
      
      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    request.sendRequest();
  }

  @Test
  public void testListapiRegularPostRequest() {

    StackMobRequest request = new StackMobRequest(StackMob
        .getInstance().getSession());

    request.methodName = "listapi";
    request.isSecure = false;
    request.isUserBased = false;
    request.httpMethod = HttpVerb.POST;
    request.callback = new StackMobCallback() {
      
      public void success(String responseBody) {
        assertNotNull(responseBody);
      }
      
      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    request.sendRequest();
  }

  @Test
  public void testInexistentMethodShouldFail() {

    StackMobRequest request = new StackMobRequest(StackMob
        .getInstance().getSession());

    request.methodName = "inexistent";
    request.isSecure = true;
    request.isUserBased = false;
    request.httpMethod = HttpVerb.GET;
    request.callback = new StackMobCallback() {
      
      public void success(String responseBody) {
        fail("Inexistent method should fail");
      }
      
      public void failure(StackMobException e) {
        assertNotNull(e.getMessage());
      }
    };

    request.sendRequest();
  }
}
