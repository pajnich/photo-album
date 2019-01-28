package com.example.photoalbum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    private boolean hudHidden;
    private TextView detailsImageTitle;
    private TextView detailsAlbumTitle;
    private TextView detailsUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initializeFields();
        initializeViews();
        setListeners();
        displayData();
    }

    private void initializeFields() {
        Intent userIntent = getIntent();
        user = new Gson().fromJson(userIntent.getStringExtra(INTENT_EXTRA_USER), User.class);
        options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_launcher_foreground);
        hudHidden = false;
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

    private void setListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hudHidden) {
                    actionBar.show();
                    detailsView.setVisibility(View.INVISIBLE);
                    hudHidden = false;
                } else {
                    actionBar.hide();
                    detailsView.setVisibility(View.VISIBLE);
                    hudHidden = true;
                }
            }
        });
    }

    private void displayData() {

        Glide.with(this).load(user.getActiveAlbum().getActivePhoto().getUrl()).apply(options).into(imageView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
