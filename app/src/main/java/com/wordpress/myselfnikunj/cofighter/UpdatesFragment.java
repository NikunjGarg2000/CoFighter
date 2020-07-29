package com.wordpress.myselfnikunj.cofighter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.wordpress.myselfnikunj.cofighter.Adapters.CountryRecyclerViewAdapter;
import com.wordpress.myselfnikunj.cofighter.Model.CountryNamesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdatesFragment extends Fragment{

    private CountryNamesModel countryModel;
    public static List<CountryNamesModel> countryNamesModelList = new ArrayList<>();
    public static boolean fromAffectedCountriesFragment;
    TextView countryNameTextView;
    TextView countryNameTotalCases;
    TextView countryNameRecoveredCases;
    TextView countryNameActiveCases;
    TextView globalActiveCases;
    TextView globalRecoveredCases;
    TextView globalTotalCases;
    TextView indianStatesInfo;
    Button assessingButton;
    Button precautionsButton;
    private View view;
    ProgressBar progressBar;
    Sprite wanderingCubes;

    RecyclerView recyclerView;
    CountryRecyclerViewAdapter countryNamesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_updates, container, false);


            progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
            wanderingCubes = new WanderingCubes();
            progressBar.setIndeterminateDrawable(wanderingCubes);
            progressBar.setVisibility(View.VISIBLE);
            fromAffectedCountriesFragment = false;
            indianStatesInfo = view.findViewById(R.id.indianStatesInfo);
            countryNameTextView = view.findViewById(R.id.countryNameTextView);
            countryNameTotalCases = view.findViewById(R.id.countryNameTotalCases);
            countryNameRecoveredCases = view.findViewById(R.id.countryNameRecoveredCases);
            countryNameActiveCases = view.findViewById(R.id.countryNameActiveCases);
            globalActiveCases = view.findViewById(R.id.globalActiveCases);
            globalRecoveredCases = view.findViewById(R.id.globalRecoveredCases);
            globalTotalCases = view.findViewById(R.id.globalTotalCases);
            recyclerView = view.findViewById(R.id.recyclerView);
            assessingButton = (Button) view.findViewById(R.id.assessingButton);
            precautionsButton = (Button) view.findViewById(R.id.precautionsButton);
            countryNamesAdapter = new CountryRecyclerViewAdapter(getContext(), countryNamesModelList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            recyclerView.setAdapter(countryNamesAdapter);

            countryNameTextView.setText(MainActivity.curCountry);

            TextView viewAll = (TextView) view.findViewById(R.id.viewAll);

            globalActiveCases.setVisibility(View.INVISIBLE);
            globalRecoveredCases.setVisibility(View.INVISIBLE);
            globalTotalCases.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            indianStatesInfo.setVisibility(View.INVISIBLE);
            precautionsButton.setVisibility(View.INVISIBLE);
            assessingButton.setVisibility(View.INVISIBLE);

            fetchData();

            viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_updatesFragment_to_affectedCountriesFragment);
                }
            });

        }

        indianStatesInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_updatesFragment_to_indianStatesFragment);

            }
        });

        assessingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("link","https://aimsindia.com/covid/");
                startActivity(intent);
            }
        });

        precautionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PrecautionsActivity.class);
                startActivity(intent);
            }
        });

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
                                try {

                                    progressBar.setVisibility(View.VISIBLE);
                                    globalTotalCases.setText(MainActivity.globalTotalCases);
                                    globalRecoveredCases.setText(MainActivity.globalRecoveredCases);
                                    globalActiveCases.setText(MainActivity.globalActiveCases);

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

                                        countryModel = new CountryNamesModel(getContext(),flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical);
                                        countryNamesModelList.add(countryModel);
                                        countryNamesAdapter.notifyDataSetChanged();
                                        if( countryNameTextView.getText().toString().equals(countryNamesModelList.get(i).getCountry())) {
                                            countryNameTotalCases.setText(countryNamesModelList.get(i).getCases());
                                            countryNameRecoveredCases.setText(countryNamesModelList.get(i).getRecovered());
                                            countryNameActiveCases.setText(countryNamesModelList.get(i).getActive());
                                            progressBar.setVisibility(View.GONE);
                                        }
                                        globalActiveCases.setVisibility(View.VISIBLE);
                                        globalRecoveredCases.setVisibility(View.VISIBLE);
                                        globalTotalCases.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        indianStatesInfo.setVisibility(View.VISIBLE);
                                        precautionsButton.setVisibility(View.VISIBLE);
                                        assessingButton.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }catch (Exception e) {
                                    globalActiveCases.setVisibility(View.VISIBLE);
                                    globalRecoveredCases.setVisibility(View.VISIBLE);
                                    globalTotalCases.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    indianStatesInfo.setVisibility(View.VISIBLE);
                                    precautionsButton.setVisibility(View.VISIBLE);
                                    assessingButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "Sorry, there is some error, Try again please :(", Toast.LENGTH_SHORT).show();
                                    Log.i("error", e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                globalActiveCases.setVisibility(View.VISIBLE);
                                globalRecoveredCases.setVisibility(View.VISIBLE);
                                globalTotalCases.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                indianStatesInfo.setVisibility(View.VISIBLE);
                                precautionsButton.setVisibility(View.VISIBLE);
                                assessingButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Log.i("error", error.getMessage());
                                Toast.makeText(getContext(), "No Internet connection! Try again with internet :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(jsonArrayRequest);
            }

    }
