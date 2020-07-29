package com.wordpress.myselfnikunj.cofighter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.wordpress.myselfnikunj.cofighter.Adapters.MediaAdapter;
import com.wordpress.myselfnikunj.cofighter.Model.HelplineModel;
import com.wordpress.myselfnikunj.cofighter.Model.MediaModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MediaFragment extends Fragment {

    private MediaAdapter mediaAdapter;
    private MediaModel mediaModel;
    private List<MediaModel> mediaModelList = new ArrayList<>();
    ListView listView;

    ProgressBar progressBar;
    Sprite wanderingCubes;

    String Url = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);
        progressBar.setVisibility(View.GONE);

        mediaAdapter = new MediaAdapter(getContext(), mediaModelList);
        listView = (ListView) view.findViewById(R.id.listView);

        fetchData();

        return view;
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=a146201568a54cd3a26f5400d70485be";
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Sorry, max API limit reached! Try again later :(", Toast.LENGTH_SHORT).show();
                }
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray articlesArray = response.getJSONArray("articles");
                                    Log.i("hehe",articlesArray.toString());

                                    for (int i = 0; i < articlesArray.length(); i++) {
                                        JSONObject jsonObject = articlesArray.getJSONObject(i);
                                        String imageLink = jsonObject.getString("urlToImage");
                                        if(imageLink == "null" || imageLink == "" || imageLink == null) {
                                            continue;
                                        } else {
                                            String title = jsonObject.getString("title");
                                            String description = jsonObject.getString("description");
                                            String url = jsonObject.getString("url");
                                            String publishedAt = jsonObject.getString("publishedAt");
                                            mediaModel = new MediaModel(getContext(), title, description, url, imageLink, publishedAt);
                                            mediaModelList.add(mediaModel);
                                        }
                                    }
                                    listView.setAdapter(mediaAdapter);
                                    mediaAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "Sorry, there is some error :(", Toast.LENGTH_SHORT).show();
                                    Log.i("error", e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("error", error.getMessage());
                        Toast.makeText(getContext(), "Sorry, max API limit reached! Try again later :(", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        },1000);

    }
}