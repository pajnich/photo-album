package com.example.photoalbum.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.photoalbum.R;
import com.example.photoalbum.network.RequestQueueSingleton;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadUsers();
    }

    private void downloadUsers() {
        String url = "https://jsonplaceholder.typicode.com/users";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY_RESPONSE", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_ERROR", error.toString());
                    }
                });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}
