# Stackmob Android SDK
This is the Official Stackmob Android SDK. This SDK builds on top of [the StackMob Java Client SDK](https://github.com/stackmob/stackmob-java-client-sdk) to provide various convenience components for Android apps.

# Getting Started

### With Maven
Maven is the best way to get set up with the SDK. You can do so with this script:

```bash
git clone git@github.com:stackmob/StackMob_Android.git
cd StackMob_Android/SDK
mvn clean install
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
3. open your project in Eclipse
4. File -> Import -> General -> Existing Projects into Workspace
5. select the StackMobDemo/SDK folder
6. ensure that the stackmob-sdk_src folder has been added in your project, and ensure that it's marked as a source folder. If it's not, follow these instructions:
	1. right click on project -> properties
	2. Java Build Path -> Source -> Add Folder
	3. select the stackmob-sdk_src folder

### Manifest Setup
After you've installed the SDK, make sure that you've enabled the INTERNET permission. Ensure that this xml is at
the top level of AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

# Coding

Since this SDK uses the [java client SDK](https://github.com/stackmob/stackmob-java-client-sdk), much of the code here will be familiar. The code below, for example, shows how to create a game object. All of the following code assumes you have your app set up correctly, and it has a game object model that matches the Game class below.

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
StackMobCommon.API_SECRET = …;
StackMobCommon.USER_OBJECT_NAME = …; //change this if you have a user object in your object model of a different name
StackMobCommon.API_VERSION = …; //0 for Sandbox, >0 for deployed
StackMob stackmob = StackMobCommon.getStackMobInstance();
```

### GET

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

### POST

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
stackmob.put("game", "charades", newGame, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

### DELETE

```java
//cancel the charades game

stackmob.delete("game", "charades", new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

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
StackMob works with Google's C2DM service [http://code.google.com/android/c2dm](http://code.google.com/android/c2dm/). To get a C2DM registration ID, follow the instructions at [http://code.google.com/android/c2dm/#registering](http://code.google.com/android/c2dm/#registering). Then, in your handleRegistration method, include this line to register the new registrationID with your servers on StackMob:

```java
stackmob.registerForPushWithUser(stackmobUsername, registrationId, new StackMobCallback() {
    @Override
    public void success(String responseBody) {}
    @Override
    public void failure(StackMobException e) {}
});
```

Note that you only should register a push notification token if you have a valid StackMob username.

Follow [these instructions](https://www.stackmob.com/platform/stackmob/help/topics/Getting-Started-with-Android-Push) to learn how to get a registration ID.

#Reporting Issues
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