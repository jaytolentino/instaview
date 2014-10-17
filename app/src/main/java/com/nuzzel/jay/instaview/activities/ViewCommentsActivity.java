package com.nuzzel.jay.instaview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nuzzel.jay.instaview.adapters.CommentsAdapter;
import com.nuzzel.jay.instaview.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ViewCommentsActivity extends Activity {
    private final String CLIENT_ID = "d48f8f8f9d594a038828a246419a8d7d";
    CommentsAdapter commentsAdapter;
    ArrayList<String> commentDataForAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        commentDataForAdapter = new ArrayList<String>();

        fetchComments();

        commentsAdapter = new CommentsAdapter(this, commentDataForAdapter);
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

    private void fetchComments() {
        String mediaId = getIntent().getStringExtra("mediaId");
        String commentsUrl = "https://api.instagram.com/v1/media/"
                + mediaId
                + "/comments?client_id="
                + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(commentsUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJson = null;
                try {
                    commentDataForAdapter.clear();
                    commentsJson = response.getJSONArray("data");
                    for (int i = 0; i < commentsJson.length(); i++) {
                        JSONObject commentData = commentsJson.getJSONObject(i);
                        String comment = createComment(commentData);
                        commentDataForAdapter.add(comment);
                    }
                    commentsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e. printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            private String createComment(JSONObject data) {
                try {
                    String fullCommentText = "<strong><font color='navy'>"
                            + data.getJSONObject("from").getString("username")
                            + "</font></strong> " + data.getString("text");
                    return fullCommentText;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }
}
