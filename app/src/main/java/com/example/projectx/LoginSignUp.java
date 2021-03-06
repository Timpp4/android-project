package com.example.projectx;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectx.fragments.ChooseFragment;
import com.example.projectx.fragments.LoginFragment;
import com.example.projectx.fragments.RegisterFragment;

import java.util.ArrayList;
import java.util.List;
// Class for choosing login fragments
public class LoginSignUp extends AppCompatActivity {


    private FragmentManager fragmentManager;
    private ChooseFragment chooseFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login_signup_layout);

        //Hiding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        chooseFragment = new ChooseFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.a_login_content, chooseFragment, chooseFragment.getClass().getName()).commit();

    }

    // Method to navigate to login fragment
    public void navigateToLogin(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideAllVisibleFragment(ft);

        if (!loginFragment.isAdded()){
            ft.replace(R.id.a_login_content, loginFragment, loginFragment.getClass().getName());
        }else{
            ft.show(loginFragment);
        }

        ft.addToBackStack(null).commit();
    }
    // Method to navigate to sign up fragment
    public void navigateToSignUp(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideAllVisibleFragment(ft);

        if (!loginFragment.isAdded()){
            ft.replace(R.id.a_login_content, registerFragment, registerFragment.getClass().getName());
        }else{
            ft.show(registerFragment);
        }
        ft.addToBackStack(null).commit();
    }


    private List<Fragment> getVisibleFragments() {

        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result
        if (chooseFragment.isVisible()) {
            result.add(chooseFragment);
        }
        if (loginFragment.isVisible()) {
            result.add(loginFragment);
        }
        if (registerFragment.isVisible()) {
            result.add(registerFragment);
        }

        return result;
    }

    private FragmentTransaction hideAllVisibleFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }
        return fragmentTransaction;
    }


}

