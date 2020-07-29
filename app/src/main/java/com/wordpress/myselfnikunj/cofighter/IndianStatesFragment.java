package com.wordpress.myselfnikunj.cofighter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wordpress.myselfnikunj.cofighter.Adapters.IndianStatesListAdapter;
import com.wordpress.myselfnikunj.cofighter.Model.IndianStatesNamesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndianStatesFragment extends Fragment {

    private IndianStatesNamesModel indianStatesNamesModel;
    private List<IndianStatesNamesModel> indianStatesNamesModelList = new ArrayList<>();
    private IndianStatesListAdapter indianStatesListAdapter;
    ListView indianStatesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indian_states, container, false);

        indianStatesListView = (ListView) view.findViewById(R.id.indianStatesListview);
        indianStatesListAdapter = new IndianStatesListAdapter(getContext(),indianStatesNamesModelList);

        fetchData();

        return view;

    }

    private void fetchData() {
        String Url = "https://api.rootnet.in/covid19-in/stats/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("regional");
                            Log.i("hehe", jsonArray.toString());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                String stateName = jsonItem.getString("loc");
                                String recovered = jsonItem.getString("discharged");
                                String cases = jsonItem.getString("totalConfirmed");
                                String deaths = jsonItem.getString("deaths");
                                String active = Integer.toString(Integer.parseInt(cases) - (Integer.parseInt(deaths) + Integer.parseInt(recovered)));
                                indianStatesNamesModel = new IndianStatesNamesModel(getContext(),stateName,cases,active,recovered);
                                indianStatesNamesModelList.add(indianStatesNamesModel);
                            }

                            indianStatesListView.setAdapter(indianStatesListAdapter);
                            indianStatesListAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.i("error", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.getMessage());
                Toast.makeText(getContext(), "Sorry, there is some error :(", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}