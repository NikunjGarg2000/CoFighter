package com.wordpress.myselfnikunj.cofighter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;

public class MapFragment extends Fragment {

    ProgressBar progressBar;
    Sprite wanderingCubes;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);

        webView = view.findViewById(R.id.assesingWebView);
        webView.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://www.healthmap.org/covid-19/");

        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        return view;
    }
}