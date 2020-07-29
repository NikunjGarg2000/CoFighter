package com.wordpress.myselfnikunj.cofighter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.wordpress.myselfnikunj.cofighter.Adapters.CountryListAdapter;
import com.wordpress.myselfnikunj.cofighter.Model.CountryNamesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountriesFragment extends Fragment {

    EditText searchEditText;
    ListView listView;

    public static List<CountryNamesModel> countryNamesModelList = new ArrayList<>();
    CountryNamesModel countryNamesModel;
    CountryListAdapter countryListAdapter;
    private View view;

    ProgressBar progressBar;
    Sprite wanderingCubes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_affected_countries, container, false);

            progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
            wanderingCubes = new WanderingCubes();
            progressBar.setIndeterminateDrawable(wanderingCubes);
            progressBar.setVisibility(View.GONE);

            searchEditText = (EditText) view.findViewById(R.id.searchEditText);
            listView = (ListView) view.findViewById(R.id.countriesListView);
            countryListAdapter = new CountryListAdapter(getContext(), countryNamesModelList);

            searchEditText.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.INVISIBLE);

            fetchData();

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    countryListAdapter.getFilter().filter(charSequence);
                    countryListAdapter.notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }

            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Bundle position = new Bundle();
                    position.putInt("position", i);
                    UpdatesFragment.fromAffectedCountriesFragment = true;
                    Navigation.findNavController(view).navigate(R.id.action_affectedCountriesFragment_to_countryDetailsFragment, position);

                }
            });
        }
            return view;
    }

    private void fetchData() {
        String Url = "https://corona.lmao.ninja/v2/countries/";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.VISIBLE);
                        try {

                            Log.i("hehe", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String recovered = jsonObject.getString("recovered");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                countryNamesModel = new CountryNamesModel(getContext(),flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical);
                                countryNamesModelList.add(countryNamesModel);
                            }

                            countryListAdapter.notifyDataSetChanged();
                            listView.setAdapter(countryListAdapter);
                            searchEditText.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        }catch (Exception e) {
                            searchEditText.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Sorry, there is some error, Try again please :(", Toast.LENGTH_SHORT).show();
                            Log.i("error", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", error.getMessage());
                        Toast.makeText(getContext(), "No Internet connection! Try again with internet :(", Toast.LENGTH_SHORT).show();                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}