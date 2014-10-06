package com.nuzzel.jay.instaview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private final String CLIENT_ID = "d48f8f8f9d594a038828a246419a8d7d";
    private ArrayList<Photo> photos;
    private PhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSwipeRefresh();

        photos = new ArrayList<Photo>();
        aPhotos = new PhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);

        fetchPopularPhotos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSwipeRefresh() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //swipeContainer.setEnabled(false);
                fetchPopularPhotos();
                swipeContainer.setRefreshing(false);
                //swipeContainer.setEnabled(true);
            }
        });
    }


    private void fetchPopularPhotos() {
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(popularUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON=  null;
                try {
                    photos.clear();
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoData = photosJSON.getJSONObject(i);
                        Photo photo = createPhoto(photoData);
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            private Photo createPhoto(JSONObject data) {
                try {
                    Photo photo = new Photo();
                    photo.author.username = data.getJSONObject("user").getString("username");
                    photo.author.profileImgUrl = data.getJSONObject("user").
                            getString("profile_picture");
                    if (!data.isNull("caption")) {
                        photo.caption = data.getJSONObject("caption").getString("text");
                    }

                    photo.imgUrl = data.getJSONObject("images")
                            .getJSONObject("standard_resolution").getString("url");
                    photo.imgHeight = data.getJSONObject("images")
                            .getJSONObject("standard_resolution").getInt("height");

                    photo.likesCount = data.getJSONObject("likes").getInt("count");

                    JSONArray commentData = data.getJSONObject("comments").getJSONArray("data");
                    photo.comment = commentData.getJSONObject(commentData.length()-1).getString("text");
                    return photo;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
