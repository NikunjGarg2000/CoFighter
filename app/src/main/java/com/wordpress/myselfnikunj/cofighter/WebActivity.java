package com.wordpress.myselfnikunj.cofighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;

public class WebActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Sprite wanderingCubes;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);

        webView = findViewById(R.id.assesingWebView);
        webView.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("link"));

        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

}
