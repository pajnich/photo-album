package com.example.photoalbum.activities;

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
import com.example.photoalbum.adapters.UsersAdapter;
import com.example.photoalbum.models.User;
import com.example.photoalbum.network.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.photoalbum.util.General.API_ENDPOINT_USERS;

public class UsersActivity extends AppCompatActivity {
    private ArrayList<User> users;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initializeFields();
        initializeViews();
        downloadAndDisplayData();
    }

    private void initializeFields() {
        users = new ArrayList<>();
    }

    private void initializeViews() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_users);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UsersAdapter(users);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Users");
        }
    }

    private void downloadAndDisplayData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_ENDPOINT_USERS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                updateUsersListWithResponse(response);
            }

            private void updateUsersListWithResponse(JSONArray response) {
                try {
                    users = extractUsersFromResponse(response);
                    adapter.setDataSet(users);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UsersActivity.this, getString(R.string.error_parsing_users), Toast.LENGTH_LONG).show();
                }
            }

            private ArrayList<User> extractUsersFromResponse(JSONArray response) throws JSONException {
                ArrayList<User> users = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    users.add(new Gson().fromJson(response.get(i).toString(), User.class));
                }
                return users;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsersActivity.this, getString(R.string.error_fetching_users), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}
