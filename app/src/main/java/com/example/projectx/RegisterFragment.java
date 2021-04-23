package com.example.projectx;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    Button signUpButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        signUpButton = (Button) v.findViewById(R.id.btn_sign);
        return v;
    }


    @Override
    public void onClick(View view) {
        //TODO: implement login logic here
    }
}