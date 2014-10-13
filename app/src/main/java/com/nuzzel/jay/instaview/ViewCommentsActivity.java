package com.nuzzel.jay.instaview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewCommentsActivity extends Activity {
    ArrayList<String> comments;
    CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        ListView lvComments = (ListView) findViewById(R.id.lvComments);

        comments = getIntent().getStringArrayListExtra("comments");
//        ArrayList<String> usernames = getIntent().getStringArrayListExtra("usernames");
//        for(int i = 0; i < contents.size(); i++) {
//            comments.add("<strong><font color='navy'>" + usernames.get(i)
//                         + "</font></strong> " + comments.get(i));
//        }

        commentsAdapter = new CommentsAdapter(this, comments);
        lvComments.setAdapter(commentsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testToast() {
        Toast.makeText(this, "TEST!", Toast.LENGTH_SHORT).show();
    }

}
