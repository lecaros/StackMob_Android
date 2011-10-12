# Stackmob Android SDK
This is the Official Stackmob Android SDK

# Getting Started

### With Maven
Maven is the best way to get set up with the SDK. Here's how:

```bash
git clone git@github.com:stackmob/StackMob_Android.git
cd StackMob_Android/SDK
mvn clean install -DskipTests #see below in "A note about testing the SDK" for why we need to skip tests
```

Then, add this to your pom.xml file:

```xml
<dependency>
    <groupId>com.stackmob.android</groupId>
    <artifactId>stackmob-sdk</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <type>apklib</type>
    <scope>compile</scope>
</dependency>
```

### Without Maven
If you don't use Maven, we have you covered as well:

1. download [this ZIP file](http://stackmob.github.com/StackMob_Android/downloads/0.0.1/StackMobDemo.zip)
2. unzip StackMobDemo.zip
3. in Eclipse, File -> Import -> General -> Existing Projects into Workspace
4. select the StackMobDemo/SDK folder

# Coding

The SDK aims to be simple to setup and use. API docs are [here](http://stackmob.github.com/StackMob_Android/javadoc/0.0.1/apidocs/), and below are some code samples that show its use. All assume that you have a StackMob server set up with a Game object model that looks like this in Java:

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
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

StackMob stackmob = StackMob.getInstance();
final String API_KEY = ...
final String API_SECRET = ...
final String APP_SUBDOMAIN = ...
final String APP_NAME = ...
final String DOMAIN = "stackmob.com";
final String USER_OBJECT_NAME = "users"; //change this if you have a user object in your object model of a different name
final Integer API_VERSION_NUM = 0; //0 for Sandbox, >0 for deployed
stackmob.setApplication(API_KEY, API_SECRET, APP_NAME, APP_SUBDOMAIN, DOMAIN, USER_OBJECT_NAME, API_VERSION_NUM);
```

### GET

```java
//get the game object for tic tac toe

Map<String, Object> arguments = new HashMap<String, Object>();
arguments.put("game_id", "tic-tac-toe");

StackMob.getInstance().get("Game", arguments, new StackMobCallback() {
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

### POST

```java
//create a new game object for charades

List<String> players = new ArrayList<String>();
players.add("John Doe");
players.add("Jane Doe");
Game g = new Game(players, "charades", System.currentTimeMillis(), System.currentTimeMillis(), "Charades - kinda boring with only 2 people!");
StackMob.getInstance().post("game", game, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException) {}
});
```

### PUT

```java
//add a player to the charades game

List<String> players = new ArrayList<String>();
players.add("John Doe");
players.add("Jane Doe");
players.add("Bob Generic")

Game newGame = new Game(players, "charades", System.currentTimeMillis(), System.currentTimeMillis(), "Charades - now less boring with 3 people!");
StackMob.getInstance().put("game", "charades", newGame, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### DELETE

```java
//cancel the charades game

StackMob.getInstance().delete("game", "charades", new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### Facebook Registration
Register a new user with a Facebook token:

```java
StackMob.getInstance().registerWithFacebookToken(fbToken, "John Doe", new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

Link an existing user to their FB account:

```java
StackMob.getInstance().linkUserWithFacebookToken(fbToken, fbTokenSecret, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### Twitter Registration
Register a new user with a Twitter token, secret, and username:

```java
StackMob.getInstance().registerWithTwitterToken(twitterToken, twitterSecret, twitterUsername, new StackMobCallback() {
   @Override
   public void success(String responseBody) {}
   @Override
   public void failure(StackMobException e) {} 
});
```

Link an existing user to their Twitter account:

```java
StackMob.getInstance().linkUserWithTwitterToken(twitterToken, twitterSecret, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### Push Notifications
StackMob works with Google's C2DM service [http://code.google.com/android/c2dm](http://code.google.com/android/c2dm/). To get a C2DM registration ID, follow the instructions at [http://code.google.com/android/c2dm/#registering](http://code.google.com/android/c2dm/#registering). Then, in your handleRegistration method, include this line to register the new registrationID with your servers on StackMob:

```java
StackMob.getInstance().registerForPushWithUser(stackmobUsername, intent.getStringExtra("registration_id"), new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
})
```

Note that you only should register a push notification token if you have a valid StackMob username. In the end, your C2DM registration code should look similar to the code in [this tutorial](http://www.vogella.de/articles/AndroidCloudToDeviceMessaging/article.html).

# Contributing
We encourage contributions to the StackMob SDK. To do so, fork this repository, make your changes and submit a pull request.

## A note about testing the SDK
The SDK test suite depends on a forked version of the Robolectric Android mocking library. If you want to run the test suite, you have to set up this Robolectric fork. Here's how:

1. in the Android SDK and AVD manager, go to "Available Packages" and select all under "Android Repository" and all under "Third Party Add-ons" -> "Google, Inc."
2. then, run this script:

```bash
git clone https://github.com/mosabua/maven-android-sdk-deployer.git #installs Android JARs into the local maven repository
cd maven-android-sdk-deployer && mvn clean install && cd ..
git clone https://github.com/Macarse/robolectric.git
cd robolectric && git checkout apacheVersion && mvn clean install -DskipTests && cd ..
```

Note that if you do not want to run tests on the SDK, make sure that you execute ```mvn clean install -DskipTests``` for your Maven builds.

Also note that if you want to run tests, you must open StackMobTestCommon.java and replace the placeholder values with your app's values.

## Forked Robolectric? What's up with that?
The stock [Robolectric library](http://pivotal.github.com/robolectric/) completely mocks all HTTP requests, so with that library, the SDK cannot be tested against real StackMob servers while running inside the JVM. We want to do both, so we rely on [https://github.com/Macarse/robolectric](this version of Robolectric) which mocks everything except HTTP requests.

We know this whole setup is far from ideal, and we're working on building a better way to test our SDK without mocking our HTTP requests. While we're working on a better solution, you should run normal (non-test) maven builds with ```-DskipTest``` so that you don't need to install custom Robolectric.

## Copyright

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