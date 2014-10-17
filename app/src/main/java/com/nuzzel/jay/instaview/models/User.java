package com.nuzzel.jay.instaview.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jay on 10/5/14.
 */
public class User {
    public String username;
    public String profileImgUrl;

    public User() {}
    public User(String username, String profileImgUrl) {
        this.username = username;
        this.profileImgUrl = profileImgUrl;
    }

    public User(JSONObject data) {
        try {
            username = data.getString("username");
            profileImgUrl = data.getString("profile_picture");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
