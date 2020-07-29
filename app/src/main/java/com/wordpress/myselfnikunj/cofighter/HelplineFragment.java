package com.wordpress.myselfnikunj.cofighter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.wordpress.myselfnikunj.cofighter.Adapters.HelplineAdapter;
import com.wordpress.myselfnikunj.cofighter.Model.HelplineModel;
import com.wordpress.myselfnikunj.cofighter.Model.IndianStatesNamesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HelplineFragment extends Fragment {

    private HelplineModel helplineModel;
    private List<HelplineModel> helplineModelList = new ArrayList<>();
    private HelplineAdapter helplineAdapter;
    ListView listView;

    ProgressBar progressBar;
    Sprite wanderingCubes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_helpline, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);

        helplineAdapter = new HelplineAdapter(getContext(), helplineModelList);
        listView = (ListView) view.findViewById(R.id.helplineListView);
        listView.setVisibility(View.INVISIBLE);

        TextView textView = (TextView) view.findViewById(R.id.callNationalHelpline);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "1075", null));
                startActivity(intent);
            }
        });

        fetchData();

        return view;
    }
    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        String Url = "https://api.rootnet.in/covid19-in/contacts";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataObject = response.getJSONObject("data");
                            JSONObject contactsObject = dataObject.getJSONObject("contacts");
                            JSONArray jsonArray = contactsObject.getJSONArray("regional");
                            Log.i("hehe", jsonArray.toString());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                String stateName = jsonItem.getString("loc");
                                String numbers = jsonItem.getString("number");
                                String[] numbersSplitted = numbers.split(",");
                                String number = numbersSplitted[0];
                                helplineModel = new HelplineModel(getContext(), stateName, number);
                                helplineModelList.add(helplineModel);
                            }

                            listView.setAdapter(helplineAdapter);
                            helplineAdapter.notifyDataSetChanged();
                            listView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            listView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Sorry, there is some error, Try again please :(", Toast.LENGTH_SHORT).show();
                            Log.i("error", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.i("error", error.getMessage());
                Toast.makeText(getContext(), "No Internet connection! Try again with internet :(", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}