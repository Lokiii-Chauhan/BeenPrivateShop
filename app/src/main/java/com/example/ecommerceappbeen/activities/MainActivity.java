package com.example.ecommerceappbeen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ecommerceappbeen.R;
import com.example.ecommerceappbeen.fragments.PostFragment;
import com.example.ecommerceappbeen.fragments.ProfileFragment;
import com.example.ecommerceappbeen.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigation;
    final Fragment fragment2 = new PostFragment();
    final Fragment fragment3 = new ProfileFragment();
    final Fragment fragment1 = new SearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.botom_navigation_bar_background_color));

        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        toolbar.setVisibility(View.GONE);
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();

        getSupportActionBar().setTitle("Home");

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setSelectedItemId(R.id.navigation_home);

        bottomNavigation.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                toolbar.setVisibility(View.GONE);
                active = fragment1;
                return true;

            case R.id.navigation_post:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                toolbar.setTitle("Add Post");
                active = fragment2;
                return true;

            case R.id.navigation_profile:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                toolbar.setTitle("Profile");
                active = fragment3;
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
//        builder.setIcon(R.mipmap.icon);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}