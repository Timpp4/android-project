package com.example.projectx;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener{

    Button loginButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button) v.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == loginButton.getId()) {
            Intent MainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(MainIntent);

        }
        //((LoginSignUp) getActivity()).MainActivity();
    }
}