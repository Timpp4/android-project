package com.example.projectx;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment implements View.OnClickListener{

    Button loginButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button) v.findViewById(R.id.btn_signup);
        loginButton.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        readAndWrite rw = new readAndWrite(getContext());

        EditText tv_user = getView().findViewById(R.id.et_email);
        System.out.println(tv_user.getText().toString());

        EditText tv_pass = getView().findViewById(R.id.et_password);
        System.out.println(tv_pass.getText().toString());

        if (rw.readFile(tv_user.getText().toString(),
                tv_pass.getText().toString()) == true) {
            // Start MainActivity after login information input
            if (view.getId() == loginButton.getId()) {
                System.out.println("loginfragmentissa..........");
                // MainActivity executes bottomNavigation fragment
                Intent MainIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(MainIntent);

            }

        }else {
            tv_pass.setError("Username or password did not match!");
        }

    }

}
