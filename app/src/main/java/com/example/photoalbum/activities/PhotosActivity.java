package com.example.photoalbum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.photoalbum.R;
import com.example.photoalbum.adapters.PhotosAdapter;
import com.example.photoalbum.models.User;
import com.google.gson.Gson;

import static com.example.photoalbum.util.General.INTENT_EXTRA_USER;

public class PhotosActivity extends AppCompatActivity {

    private User user;
    private PhotosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        initializeFields();
        initializeViews();
        displayData();
    }

    private void initializeFields() {
        Intent userIntent = getIntent();
        user = new Gson().fromJson(userIntent.getStringExtra(INTENT_EXTRA_USER), User.class);
    }

    private void initializeViews() {
        int spanCount = determineSpanCountBasedOnDeviceDimensions();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_photos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotosAdapter(user);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Photos");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void displayData() {
        adapter.setDataSet(user);
        adapter.notifyDataSetChanged();
    }

    private int determineSpanCountBasedOnDeviceDimensions() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (dpWidth / 150);
        return spanCount;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
