package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectx.backend.readAndWrite;
import com.example.projectx.fragments.HomeFragment;
import com.example.projectx.fragments.InfoFragment;
import com.example.projectx.fragments.LoginFragment;
import com.example.projectx.fragments.ProfileFragment;
import com.example.projectx.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    //Context context = this.getApplicationContext();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_navigation_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.container, new LoginFragment()).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();


    }
    // Create BottomNavigationBar and fragments to application menu
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.home:
                            fragment = new HomeFragment();
                            break;

                        case R.id.info:
                            fragment = new InfoFragment();
                            break;

                        case R.id.profile:
                            fragment = new ProfileFragment();
                            break;

                        case R.id.settings:
                            fragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };


}