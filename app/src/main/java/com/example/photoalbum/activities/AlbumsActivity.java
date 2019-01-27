package com.example.photoalbum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.photoalbum.R;
import com.example.photoalbum.adapters.AlbumsAdapter;
import com.example.photoalbum.models.Album;
import com.example.photoalbum.models.Photo;
import com.example.photoalbum.models.User;
import com.example.photoalbum.network.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.photoalbum.util.General.API_ENDPOINT_ALBUMS;
import static com.example.photoalbum.util.General.API_ENDPOINT_PHOTOS;
import static com.example.photoalbum.util.General.INTENT_EXTRA_USER;

public class AlbumsActivity extends AppCompatActivity {
    private User user;
    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        initializeFields();
        initializeViews();
        downloadAndDisplayData();
    }

    private void initializeFields() {
        Intent userIntent = getIntent();
        user = new Gson().fromJson(userIntent.getStringExtra(INTENT_EXTRA_USER), User.class);
    }

    private void initializeViews() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_albums);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlbumsAdapter(user);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Albums");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void downloadAndDisplayData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_ENDPOINT_ALBUMS + "?userId=" + user.getId(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                updateUserAlbumsWithResponse(response);
            }

            private void updateUserAlbumsWithResponse(JSONArray response) {
                try {
                    user.setAlbums(extractAlbumsFromResponse(response));
                    adapter.setDataSet(user);
                    adapter.notifyDataSetChanged();
                    downloadAndDisplayAlbumThumbnails(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AlbumsActivity.this, getString(R.string.error_parsing_albums), Toast.LENGTH_LONG).show();
                }
            }

            private ArrayList<Album> extractAlbumsFromResponse(JSONArray response) throws JSONException {
                ArrayList<Album> albums = new ArrayList<>();
                Album album;
                for (int i = 0; i < response.length(); i++) {
                    album = new Gson().fromJson(response.get(i).toString(), Album.class);
                    albums.add(album);
                }
                return albums;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumsActivity.this, getString(R.string.error_fetching_albums), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void downloadAndDisplayAlbumThumbnails(User user) {
        Album album;
        int albumId;
        for (int i = 0; i < user.getAlbums().size(); i++) {
            album = user.getAlbums().get(i);
            albumId = album.getId();
            downloadPhotosAndDisplayThumbnailForAlbum(albumId, i);
        }
    }

    private void downloadPhotosAndDisplayThumbnailForAlbum(final int albumId, final int albumPosition) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_ENDPOINT_PHOTOS + "?albumId=" + albumId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                updatePhotosForAlbumFromResponse(albumPosition, response);
            }

            private void updatePhotosForAlbumFromResponse(int albumPosition, JSONArray response) {
                try {
                    ArrayList<Photo> photos = extractPhotosForAlbumFromResponse(response);
                    user.getAlbums().get(albumPosition).setPhotos(photos);
                    adapter.setDataSet(user);
                    adapter.notifyItemChanged(albumPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AlbumsActivity.this, getString(R.string.error_parsing_photos), Toast.LENGTH_LONG).show();
                }
            }

            private ArrayList<Photo> extractPhotosForAlbumFromResponse(JSONArray response) throws JSONException {
                ArrayList<Photo> photos = new ArrayList<>();
                Photo photo;
                for (int i = 0; i < response.length(); i++) {
                    photo = new Gson().fromJson(response.get(i).toString(), Photo.class);
                    photos.add(photo);
                }
                return photos;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumsActivity.this, getString(R.string.error_fetching_albums), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
