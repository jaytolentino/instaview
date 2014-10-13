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
    public ArrayList<String> commentContents;
    public ArrayList<String> commentUsernames;

    public Photo() {
        author = new User();
        comments = new ArrayList<Comment>();
        commentContents = new ArrayList<String>();
    }

    public void addComment(Comment c) {
        comments.add(c);
        commentContents.add(c.content);
        commentUsernames.add(c.author.username);
    }

    public Comment getLastComment() {
        return comments.get(comments.size() - 1);
    }
}
