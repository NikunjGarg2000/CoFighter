package com.wordpress.myselfnikunj.cofighter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class PrecautionsActivity extends AppCompatActivity {

    ImageView fightImageView;
    ImageView stayHomeImageView;
    ImageView handwashImageView;
    ImageView twoMeterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions);

        fightImageView = (ImageView) findViewById(R.id.fightImageView);
        Glide.with(getApplicationContext()).load(R.drawable.gif_fight).apply(new RequestOptions().placeholder(R.drawable.gif_fight)).into(new DrawableImageViewTarget(fightImageView));

        stayHomeImageView = (ImageView) findViewById(R.id.stayHomeImageView);
        Glide.with(getApplicationContext()).load(R.drawable.gif_stayhome).apply(new RequestOptions().placeholder(R.drawable.gif_stayhome)).into(new DrawableImageViewTarget(stayHomeImageView));

        handwashImageView = (ImageView) findViewById(R.id.handwashImageView);
        Glide.with(getApplicationContext()).load(R.drawable.gif_handwash).apply(new RequestOptions().placeholder(R.drawable.gif_handwash)).into(new DrawableImageViewTarget(handwashImageView));

        twoMeterImageView = (ImageView) findViewById(R.id.twoMeterImageView);
        Glide.with(getApplicationContext()).load(R.drawable.gif_2m).apply(new RequestOptions().placeholder(R.drawable.gif_2m)).into(new DrawableImageViewTarget(twoMeterImageView));

    }

    public void morePrecautions(View view) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        intent.putExtra("link","https://www.cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/prevention.html/");
        startActivity(intent);
        finish();
    }
}