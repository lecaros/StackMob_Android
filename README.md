# Stackmob Android SDK
This is the Official Stackmob Android SDK. It builds on top of the [StackMob Java Client SDK](https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started:-Java-Client-SDK) to provide various convenience components specifically for Android apps. The SDK aims to provide compatability for Android 2.2 and above.

# Getting Started - New App

<span class="snippet" title="androidsetup"/>

Getting started on a new Android App is as simple as opening both the SDK and Demo projects in Eclipse, and doing your work in the Demo project.

# Getting Started - Existing App
If you already have an app, follow these instructions to add the StackMob SDK to it.

## Installing the SDK in Eclipse
Here's how to get set up with the SDK. These instructions assume you're using Eclipse for Android development.

1. git://github.com/stackmob/StackMob_Android.git
2. Right click on your Eclipse project -> Import -> Existing Projects into Workspace -> Select Root Directory -> Browse
3. Select StackMob_Android/SDK for the root directory. This should result in a new project in your workspace called "TwitterActivity".
4. Right click on your your Eclipse project -> Build Path -> Link Source -> Browse
5. Select StackMob_Android/SDK/src, enter "SDK_src" under Folder Name and click Finish
6. Click on Libraries -> Add JARs
7. Select all the JARs under TwitterActivity -> assets
8. Click Ok.
9. Right click on your project's assets directory -> Import -> General -> File System -> Next
10. Click browse and select StackMob_Android/SDK/assets as the root directory.
11. Click on assets, select all of the *.jar files, and click Finish

## Manifest Setup
After you've installed the SDK & installed all of its dependencies, make sure that you've enabled the INTERNET permission. Ensure that this xml is at
the top level of AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

<span class="snippet"/>

## Optional: Using IntelliJ 

If you instead wish to use IntelliJ IDEA for Android development, follow these steps:

1. Clone or download git://github.com/stackmob/StackMob_Android.git
2. In IntelliJ, open your Android application project, or create a new one.
3. Select File -> New Module -> Create new module from scratch
4. For the location of the project files, enter the path to StackMob_Android/SDK, which you downloaded earlier.
5. Click next. IntelliJ should find the source directories.
6. Click next, and Uncheck "Create default Android application structure"
7. Select Finish
8. Right click on the new SDK module and select "Module settings", and go to the dependencies tab
9. Select Add... -> Library -> New Library... -> Attach Jar Files
10. Choose the assets folder in the SDK directory and apply changes
11. In the same dialog, go to the module for your application, and select the dependencies tab
12. Select Add... -> Module Dependency and select the SDK Module
13. Save your changes
14. Setup your Manifest as mentioned above.

Please note that IntelliJ support is currently experimental. Please let us know if you encounter any issues using IntelliJ with the StackMob Android SDK.


# Making REST API Calls

Since this SDK uses the [StackMob Java Client SDK](https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started:-Java-Client-SDK), much of the code here will be similar to that used in the Java SDK. The code below, for example, shows how to create a game object. All of the following code assumes you have your app set up correctly, and it has a game object model that matches the Game class below.

```java
import java.util.List;

class Game {
  public List<String> players;
  public String game_id;
  public long createddate;
  public long lastmoddate;
  public String name; 
  
  public Game(List<String> players, String game_id, long createddate, long lastmoddate, String name) {
      this.players = players;
      this.game_id = game_id;
      this.createddate = createddate;
      this.lastmoddate = lastmoddate;
      this.name = name;
  }
}
```

### Setup

```java
import com.stackmob.android.sdk.api.StackMob;
import com.stackmob.android.sdk.api.StackMob;
import com.stackmob.android.sdk.callback.StackMobCallback;
import com.stackmob.android.sdk.exception.StackMobException;
import com.stackmob.android.sdk.common.StackMobCommon;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

StackMobCommon.API_KEY = …;
StackMobCommon.API_SECRET = …;
StackMobCommon.USER_OBJECT_NAME = …; //change this if you have a user object in your object model of a different name
StackMobCommon.API_VERSION = …; //0 for Sandbox, >0 for deployed
StackMob stackmob = StackMobCommon.getStackMobInstance();
```

### GET

<span class="snippet" title="Android GET"/>

```java
//get the game object for tic tac toe

Map<String, Object> arguments = new HashMap<String, Object>();
arguments.put("game_id", "tic-tac-toe");

stackmob.get("Game", arguments, new StackMobCallback() {
    @Override
    public void success(String responseBody) {
        System.out.println("got object with response: " + responseBody);
    }
    @Override
    public void failure(StackMobException e) {
        System.out.println("failed to get object with exception: " + e.toString());
    }
});
```

<span class="snippet"/>

### POST

<span class="snippet" title="Android POST"/>

```java
//create a new game object for charades

List<String> players = new ArrayList<String>();
players.add("John Doe");
players.add("Jane Doe");
Game g = new Game(players, "charades", System.currentTimeMillis(), System.currentTimeMillis(), "Charades - kinda boring with only 2 people!");
stackmob.post("game", game, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

<span class="snippet"/>

### PUT

<span class="snippet" title="Android POST"/>

```java
//add a player to the charades game

List<String> players = new ArrayList<String>();
players.add("John Doe");
players.add("Jane Doe");
players.add("Bob Generic")

Game newGame = new Game(players, "charades", System.currentTimeMillis(), System.currentTimeMillis(), "Charades - now less boring with 3 people!");
stackmob.put("game", "charades", newGame, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

<span class="snippet"/>

### DELETE

<span class="snippet" title="Android DELETE"/>

```java
//cancel the charades game

stackmob.delete("game", "charades", new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

<span class="snippet"/>

### Advanced Queries

**Additional advanced queries are available in the Java Client SDK docs**

The Android SDK is built upon the <a href="https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started:-Java-Client-SDK" target="_blank">Java Client SDK</a>.

<a href="https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started:-Java-Client-SDK#a-advanced_queries" target="_blank">Read how to **perform range queries**, **query for multiple values**, **expand relationships**, and more in the Java Client SDK docs.</a>

### Facebook Registration
Register a new user with a Facebook token:

```java
stackmob.registerWithFacebookToken(fbToken, "John Doe", new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

Link an existing user to their FB account:

```java
stackmob.linkUserWithFacebookToken(fbToken, fbTokenSecret, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### Twitter Registration
Register a new user with a Twitter token, secret, and username:

```java
stackmob.registerWithTwitterToken(twitterToken, twitterSecret, twitterUsername, new StackMobCallback() {
   @Override
   public void success(String responseBody) {}
   @Override
   public void failure(StackMobException e) {} 
});
```

Link an existing user to their Twitter account:

```java
stackmob.linkUserWithTwitterToken(twitterToken, twitterSecret, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### Push Notifications
StackMob works with Google's C2DM service [http://code.google.com/android/c2dm](http://code.google.com/android/c2dm/) to do push notifications on Android. For a complete overview on how to set up push, check out the [StackMob  Push Docs](https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started-with-Android-Push).

Also, the [stackmob-java-client-sdk](https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started:-Java-Client-SDK) fully supports the [StackMob REST API for Push](http://stackmob.github.com/stackmob-java-client-sdk/javadoc/0.1.4/apidocs/), so the StackMob Android SDK has full access to those APIs.

# Reporting Issues
We use Github to track issues with the SDK. If you find any issues, please report them [here](https://github.com/stackmob/StackMob_Android/issues), and include as many details as possible about the issue you encountered.

# Contributing
We encourage contributions to the StackMob SDK. If you'd like to contribute, fork this repository, make your changes and submit a pull request with your changes.

# Copyright

Copyright 2011 StackMob

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.