package com.nuzzel.jay.instaview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPopularPhotos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void fetchPopularPhotos() {
        photos = new ArrayList<Photo>();
        aPhotos = new PhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);

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
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            private Photo createPhoto(JSONObject data) {
                try {
                    Photo photo = new Photo();

                    photo.username = data.getJSONObject("user").getString("username");

                    if (data.getJSONObject("caption") != null) {
                        photo.caption = data.getJSONObject("caption").getString("text");
                    }

                    photo.imgUrl = data.getJSONObject("images")
                            .getJSONObject("standard_resolution").getString("url");
                    photo.imgHeight = data.getJSONObject("images")
                            .getJSONObject("standard_resolution").getInt("height");

                    photo.likesCount = data.getJSONObject("likes").getInt("count");
                    return photo;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
