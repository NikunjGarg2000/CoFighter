package com.wordpress.myselfnikunj.cofighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    public static String curCountry = "";
    public static String globalTotalCases = "";
    public static String globalActiveCases = "";
    public static String globalRecoveredCases = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load(R.drawable.gif_fight).apply(new RequestOptions().placeholder(R.drawable.gif_fight)).into(new DrawableImageViewTarget(imageView));
        getLocation();
        fetchData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        },3000);
    }
    //Get closest city name
    public String hereLocation(double lat, double lon) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                curCountry = addressList.get(0).getCountryName();
            }
        } catch (Exception e) {
            curCountry = "India";
            e.printStackTrace();
        }
        return curCountry;
    }

    public void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                hereLocation(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                curCountry = "India";
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            hereLocation(location.getLatitude(), location.getLongitude());
                        } catch (Exception e) {
                            curCountry = "India";
                            e.printStackTrace();
                        }
                    } else {
                        curCountry = "India";
                    }
                }
            }
        }
    }

    public void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            globalTotalCases = jsonObject.getString("cases");
                            globalRecoveredCases = jsonObject.getString("recovered");
                            globalActiveCases = jsonObject.getString("active");

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Sorry, there is some error, Try again please :(", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Internet Connection! Try again with internet :(", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}