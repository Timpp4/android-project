package com.example.projectx.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectx.R;
import com.example.projectx.backend.readAndWrite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    private Button logOutButton;
    private Button updateButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutButton = (Button) v.findViewById(R.id.btn_logOut);
        logOutButton.setOnClickListener(this);
        updateButton = (Button) v.findViewById(R.id.btn_update);
        updateButton.setOnClickListener(this);
        update(v);



        return v;
    }
    //Log out logic
    @Override
    public void onClick(View view) {
        if (view.getId() == logOutButton.getId()) {
            getActivity().finish();
            //((LoginSignUp) getActivity()).navigateToLogin();
        }
    }
    //Update profile information
    public void update(View view) {
        readAndWrite rw = new readAndWrite(getContext());
        List<String> infoList = new ArrayList<String>();
        infoList = rw.profileInfo();
        System.out.println(infoList);

    }







}

