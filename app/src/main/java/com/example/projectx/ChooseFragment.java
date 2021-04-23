package com.example.projectx;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ChooseFragment extends Fragment implements View.OnClickListener {

    private Button signUpButton;
    private Button logInButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose, container, false);
        logInButton = (Button) v.findViewById(R.id.btn_login);
        logInButton.setOnClickListener(this);
        signUpButton = (Button) v.findViewById(R.id.btn_sign);
        signUpButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == logInButton.getId()) {
            ((MainActivity) getActivity()).navigateToLogin();
        } else if (view.getId() == signUpButton.getId()) {
            ((MainActivity) getActivity()).navigateToSignUp();
        }
    }
}