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

import com.xtremelabs.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static StackMobTestCommon.*;

@RunWith(RobolectricTestRunner.class)
public class StackMobSessionTest {

  @BeforeClass
  public static void onlyOnce() {
    StackMob stackmob = StackMob.getInstance();
    stackmob.setApplication(API_KEY, API_SECRET, APP_NAME, SUBDOMAIN, DOMAIN, USER_OBJECT_NAME, API_VERSION);
  }

  @Test
  public void testSessionInitializedCorrectly() {
    StackMobSession session = StackMob.getInstance().getSession();
    assertEquals(API_KEY, session.getKey());
    assertEquals(API_SECRET, session.getSecret());
    assertEquals(APP_NAME, session.getAppName());
    assertEquals(SUBDOMAIN, session.getSubDomain());
    assertEquals(DOMAIN, session.getDomain());
    assertEquals(USER_OBJECT_NAME, session.getUserObjectName());
    assertEquals(API_VERSION, session.getApiVersionNumber());
  }
}
