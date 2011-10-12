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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail; 

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackmob.android.sdk.callback.StackMobCallback;
import com.stackmob.android.sdk.exception.StackMobException;
import static StackMobTestCommon.*;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class StackMobTest {

  @BeforeClass
  public static void onlyOnce() {
    StackMob stackmob = StackMob.getInstance();
    stackmob.setApplication(API_KEY, API_SECRET, APP_NAME, SUBDOMAIN, DOMAIN, USER_OBJECT_NAME, API_VERSION);
  }

  @Test
  public void testSingleton() {
    assertNotNull(StackMob.getInstance());
  }

  @Test
  public void testLoginShouldBeSucessful() {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("username", "admin");
    params.put("password", "1234");

    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        assertNotNull(responseBody);
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    StackMob.getInstance().login(params, callback);
  }

  @Test
  public void testLoginShouldFail() {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("username", "idonotexist");
    params.put("password", "ghost");

    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        fail("Login shouldn't succeed when login credentials are wrong");
      }

      public void failure(StackMobException e) {
        assertEquals("Unauthorized", e.getMessage());
      }
    };

    StackMob.getInstance().login(params, callback);
  }

  @Test
  public void testLogoutShouldBeSucessful() {

    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("username", "admin");
    params.put("password", "1234");
    StackMob.getInstance().login(params, new StackMobCallback() {

      public void success(String responseBody) {
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    });

    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        assertNotNull(responseBody);
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    StackMob.getInstance().logout(callback);
  }

  @Test
  public void startSessionTest() {
    StackMob.getInstance().startSession(Robolectric.application,
        new StackMobCallback() {

          public void success(String responseBody) {
            assertNotNull(responseBody);
          }

          public void failure(StackMobException e) {
            fail(e.getMessage());
          }
        });
  }

  //@Test
  // Not working, returning 404
  public void endSessionTest() {
    StackMob.getInstance().endSession(new StackMobCallback() {

      public void success(String responseBody) {
        System.out.println("endsession: " + responseBody);
        assertNotNull(responseBody);
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    });
  }

  public static class Game {
    public List<String> players;
    public String game_id;
    public long createddate;
    public long lastmoddate;
    public String name;
  }

  @Test
  public void testGetWithoutArguments() {
    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        assertNotNull(responseBody);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Game>>() {
        }.getType();
        List<Game> games = gson.fromJson(responseBody, collectionType);

        assertNotNull(games);
        assertFalse(games.isEmpty());
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    StackMob.getInstance().get("game", callback);
  }

  @Test
  public void testGetWithArguments() {
    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        assertNotNull(responseBody);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Game>>() {
        }.getType();
        List<Game> games = gson.fromJson(responseBody, collectionType);

        assertNotNull(games);
        assertFalse(games.size() > 1);

        Game game = games.get(0);
        assertEquals("one", game.name);

      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    HashMap<String, Object> arguments = new HashMap<String, Object>();
    arguments.put("name", "one");
    StackMob.getInstance().get("game", arguments, callback);
  }

  @Test
  public void testPostWithRequestObject() {
    StackMobCallback callback = new StackMobCallback() {

      public void success(String responseBody) {
        Gson gson = new Gson();
        Game game = gson.fromJson(responseBody, Game.class);

        assertEquals("newGame", game.name);
      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    };

    Game game = new Game();
    game.name = "newGame";
    StackMob.getInstance().post("game", game, callback);
  }

  @Test
  public void testDeleteWithId() {
    Game game = new Game();
    game.name = "gameToDelete";

    StackMob.getInstance().post("game", game, new StackMobCallback() {

      public void success(String responseBody) {
        Gson gson = new Gson();
        Game game = gson.fromJson(responseBody, Game.class);

        StackMob.getInstance().delete("game", game.game_id,
            new StackMobCallback() {

              public void success(String responseBody) {
                assertNotNull(responseBody);
              }

              public void failure(StackMobException e) {
                fail(e.getMessage());
              }
            });

      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    });
  }

  @Test
  public void testPutWithIdAndObjectRequest() {
    Game game = new Game();
    game.name = "gameToModifyName";

    StackMob.getInstance().post("game", game, new StackMobCallback() {

      public void success(String responseBody) {
        Gson gson = new Gson();
        Game game = gson.fromJson(responseBody, Game.class);

        Game gameWithOtherName = new Game();
        gameWithOtherName.name = "otherName";

        StackMob.getInstance().put("game", game.game_id,
            gameWithOtherName, new StackMobCallback() {

              public void success(String responseBody) {
                Gson gson = new Gson();
                Game game = gson.fromJson(responseBody, Game.class);
                assertNotNull(game);
                assertEquals("otherName", game.name);
              }

              public void failure(StackMobException e) {
                fail(e.getMessage());
              }
            });

      }

      public void failure(StackMobException e) {
        fail(e.getMessage());
      }
    });
  }
}
