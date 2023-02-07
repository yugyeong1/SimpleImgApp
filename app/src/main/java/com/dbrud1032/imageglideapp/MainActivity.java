package com.dbrud1032.imageglideapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dbrud1032.imageglideapp.adapter.ProfileAdapter;
import com.dbrud1032.imageglideapp.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com";


    RecyclerView recyclerView;
    ProfileAdapter adapter;
    ArrayList<Profile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        // 네트워크 통해서 데이터를 받아오고, 화면에 표시한다.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/photos.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.i("PROFILE_APP", response.getJSONArray("data").toString());
                            Log.i("PROFILE_APP", response.getJSONArray("data").getJSONObject(1).toString());

                            JSONArray data = response.getJSONArray("data");

                            for ( int i=0; i<data.length(); i++ ){

                                Profile profile = new Profile(
                                        data.getJSONObject(i).getInt("albumId"),
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("url"),
                                        data.getJSONObject(i).getString("thumbnailUrl"));

                                profileList.add(profile);
                            }

                        } catch (JSONException e) {
                            return;
                        }

                        adapter = new ProfileAdapter(MainActivity.this, profileList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        // 네트워크 호출!
        queue.add(request);

    }

    }
