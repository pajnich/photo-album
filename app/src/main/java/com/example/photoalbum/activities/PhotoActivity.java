package com.example.photoalbum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.photoalbum.R;
import com.example.photoalbum.models.User;
import com.google.gson.Gson;

import static com.example.photoalbum.util.General.INTENT_EXTRA_USER;

public class PhotoActivity extends AppCompatActivity {

    private User user;
    private RequestOptions options;
    private ImageView imageView;
    private ActionBar actionBar;
    private CardView detailsView;
    private TextView detailsImageTitle;
    private TextView detailsAlbumTitle;
    private TextView detailsUserName;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initializeFields();
        initializeViews();
        displayData();
    }

    private void initializeFields() {
        Intent userIntent = getIntent();
        user = new Gson().fromJson(userIntent.getStringExtra(INTENT_EXTRA_USER), User.class);
        options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_launcher_foreground);
        gestureDetector = new GestureDetectorCompat(this, new GestureListener());
    }

    private void initializeViews() {
        imageView = findViewById(R.id.activity_photo_image);
        actionBar = getSupportActionBar();
        detailsView = findViewById(R.id.activity_photo_details);
        detailsImageTitle = findViewById(R.id.activity_photo_details_image_title);
        detailsAlbumTitle = findViewById(R.id.activity_photo_details_album_title);
        detailsUserName = findViewById(R.id.activity_photo_details_user_name);
        if (actionBar != null) {
            actionBar.setTitle("Photo");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void displayData() {
        detailsImageTitle.setText(user.getActiveAlbum().getActivePhoto().getTitle());
        detailsAlbumTitle.setText(user.getActiveAlbum().getTitle());
        detailsUserName.setText(user.getName());
        Glide.with(this).load(user.getActiveAlbum().getActivePhoto().getUrl()).apply(options).into(imageView);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (actionBar.isShowing()) {
                hideHud();
            } else {
                showHud();
            }
            return false;
        }
    }

    private void hideHud() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        detailsView.setVisibility(View.GONE);
    }

    private void showHud() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        detailsView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }
}
