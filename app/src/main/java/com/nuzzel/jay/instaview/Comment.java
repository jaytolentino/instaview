package com.nuzzel.jay.instaview;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jay on 10/6/14.
 */
public class Comment implements Parcelable, Serializable {
    public String content;
    public User author;

    public Comment() {
        author = new User();
    }
    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.describeContents());
        out.writeSerializable(this);
    }
}
