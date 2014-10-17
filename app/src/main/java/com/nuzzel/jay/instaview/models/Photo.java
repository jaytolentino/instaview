package com.nuzzel.jay.instaview.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 10/2/14.
 */
public class Photo implements Serializable {
    private User mAuthor;
    private String mCaption;
    private String mImgUrl;
    private int mLikesCount;
    private int mCommentCount;
    private String mMediaId;
    private Comment mLastComment;

    public Photo(JSONObject data) {
        try {
            mAuthor = new User(data.getJSONObject("user"));
            if (!data.isNull("caption")) {
                mCaption = data.getJSONObject("caption").getString("text");
            }
            mImgUrl = data.getJSONObject("images").getJSONObject("standard_resolution")
                    .getString("url");
            mLikesCount = data.getJSONObject("likes").getInt("count");
            mMediaId = data.getString("id");
            mCommentCount = data.getJSONObject("comments").getInt("count");

            JSONArray commentData = data.getJSONObject("comments").getJSONArray("data");
            JSONObject lastCommentData = commentData.getJSONObject(commentData.length() - 1);
            setDataForLastComment(lastCommentData);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public User getAuthor() {
        return mAuthor;
    }

    public void setAuthor(User author) {
        mAuthor = author;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public String getMediaId() {
        return mMediaId;
    }

    public void setMediaId(String mediaId) {
        this.mMediaId = mediaId;
    }

    public Comment getLastComment() {
        return mLastComment;
    }

    public void setLastComment(Comment lastComment) {
        mLastComment = lastComment;
    }

    private void setDataForLastComment(JSONObject lastCommentData) {
        try {
            User commentAuthor = new User(
                    lastCommentData.getJSONObject("from").getString("username"),
                    lastCommentData.getJSONObject("from").getString("profile_picture")
            );
            String content = lastCommentData.getString("text");
            mLastComment = new Comment(commentAuthor, content);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
