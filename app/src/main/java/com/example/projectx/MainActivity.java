package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LineChart mpLineChart;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private ChooseFragment chooseFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login_signup_layout);

        chooseFragment = new ChooseFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        //getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.container, new LoginFragment()).commit();
        //bottomNavigationView = findViewById(R.id.bottomNavigation);
        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.a_login_content, chooseFragment, chooseFragment.getClass().getName()).commit();

    }


    public void navigateToLogin(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (!loginFragment.isAdded()){
            ft.add(R.id.a_login_content, loginFragment, loginFragment.getClass().getName());
        }else{
            ft.show(loginFragment);
        }
        ft.addToBackStack(null).commit();
    }

    public void navigateToSignUp(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (!loginFragment.isAdded()){
            ft.add(R.id.a_login_content, registerFragment, registerFragment.getClass().getName());
        }else{
            ft.show(registerFragment);
        }
        ft.addToBackStack(null).commit();
    }


    /*private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new
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
            };*/


}