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

package com.stackmob.android.sdk.common;

import com.stackmob.android.sdk.callback.*;
import com.stackmob.sdk.api.StackMob;

public class StackMobCommon {
	public static String API_KEY = "YOUR_API_KEY_HERE";
	public static String API_SECRET = "YOUR_API_SECRET_HERE";
	public static String USER_OBJECT_NAME = "YOUR_USER_OBJECT_NAME_HERE";
	public static Integer API_VERSION = 0;
	
	public static String TWITTER_CONSUMER_KEY = "YOUR_TWITTER_CONSUMER_KEY_HERE";
	public static String TWITTER_CONSUMER_SECRET = "YOUR_TWITTER_CONSUMER_SECRET_HERE";
	
	public static String FACEBOOK_APP_ID = "YOUR_FACEBOOK_APP_ID_HERE";
	
	private static StackMob stackmob;
	
	public static StackMob getStackMobInstance() {
		if(stackmob == null) {
			stackmob = new StackMob(API_KEY, API_SECRET, USER_OBJECT_NAME, API_VERSION);
		}
		return stackmob;
	}
	
	public static StackMobTwitterCallback TwitterCallback = null;
	public static StackMobFacebookCallback FacebookCallback = null;
}
