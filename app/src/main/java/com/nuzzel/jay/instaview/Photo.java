package com.nuzzel.jay.instaview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 10/2/14.
 */
public class Photo implements Serializable {
    public User author;
    public String caption;
    public String imgUrl;
    public int likesCount;
    public List<Comment> comments;
    public int commentCount;
    public String mediaId;
    public Comment lastComment;

    public Photo() {
        author = new User();
        comments = new ArrayList<Comment>();
    }
}
