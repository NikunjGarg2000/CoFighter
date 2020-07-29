package com.wordpress.myselfnikunj.cofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    NavController navController;
    NavigationView drawerNavigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Intent intent;
    public static boolean isDarkMode = false;
//    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        sharedPreferences = getPreferences(MODE_PRIVATE);

//        isDarkMode = true;
//
//        if (isDarkMode) {
//            isDarkMode = false;
//        } else {
//            isDarkMode = true;
//        }
//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerNavigationView = (NavigationView) findViewById(R.id.drawerNavigationView);
        drawerNavigationView.bringToFront();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.callHelpline:
                        Navigation.findNavController(HomeActivity.this,R.id.fragment).navigate(R.id.helplineFragment);
                        break;
                    case R.id.assessYourself:
                        intent = new Intent(getApplicationContext(), WebActivity.class);
                        intent.putExtra("link","https://aimsindia.com/covid/");
                        startActivity(intent);
                        break;
                    case R.id.precautions:
                        intent = new Intent(getApplicationContext(), PrecautionsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shareApp:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareSub = "CoFighter App";
                        String shareBody = "https://drive.google.com/file/d/1yMsrRy_d-MAyyLuHRWucPbt9o14IwBf3/view?usp=sharing";
                        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(intent.createChooser(intent, "Share With"));                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.darkMode:
                if (isDarkMode) {
                    isDarkMode = false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    isDarkMode = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;
            case R.id.call:
                Navigation.findNavController(this,R.id.fragment).navigate(R.id.helplineFragment);
                break;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareSub = "CoFighter App";
                String shareBody = "https://drive.google.com/file/d/1yMsrRy_d-MAyyLuHRWucPbt9o14IwBf3/view?usp=sharing";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(intent.createChooser(intent, "Share With"));
        }
        return super.onOptionsItemSelected(item);
    }
}