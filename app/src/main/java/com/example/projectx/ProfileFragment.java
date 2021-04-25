package com.example.projectx;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    private Button logOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutButton = (Button) v.findViewById(R.id.btn_logOut);
        logOutButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        // Fragment navigation logic
        if (view.getId() == logOutButton.getId()) {
            getActivity().finish();
            //((LoginSignUp) getActivity()).navigateToLogin();
        }
    }
}

