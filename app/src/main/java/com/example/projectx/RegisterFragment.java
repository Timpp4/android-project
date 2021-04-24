package com.example.projectx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//vanha
public class RegisterFragment extends Fragment implements View.OnClickListener{
//public class RegisterFragment extends Fragment {

    public Button signUpButton;
    Spinner spinner;
    public View paramView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        signUpButton = (Button) v.findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(this);
        Spinner spinner = v.findViewById(R.id.et_sexSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.genders_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return v;
    }


    @Override
    public void onClick(View view) {

        readAndWrite rw = new readAndWrite(getContext());

        EditText tv_user = getView().findViewById(R.id.et_username);
        System.out.println(tv_user.getText().toString());

        EditText tv_pass = getView().findViewById(R.id.et_password);
        System.out.println(tv_pass.getText().toString());

        EditText tv_height = getView().findViewById(R.id.et_height);
        System.out.println(tv_height.getText().toString());
        double height =  Double.parseDouble(tv_height.getText().toString());
        System.out.println(height);

        EditText tv_weight = getView().findViewById(R.id.et_weight);
        System.out.println(tv_weight.getText().toString());
        double weight = Double.valueOf(tv_weight.getText().toString());
        System.out.println(weight);

        EditText tv_age = getView().findViewById(R.id.et_age);
        System.out.println(tv_weight.getText().toString());
        int age = Integer.valueOf(tv_age.getText().toString());
        System.out.println(age);

        Spinner tv_sex = (Spinner) getView().findViewById(R.id.et_sexSpinner);
        String gender = tv_sex.getSelectedItem().toString();
        System.out.println(gender);

        if (rw.createNewUser(tv_user.getText().toString(),
                tv_pass.getText().toString(),
                height,
                weight,
                age,
                gender) == true) {

            if (view.getId() == signUpButton.getId()) {
                ((LoginSignUp) getActivity()).navigateToLogin();
                System.out.println("pääohjelmassa true, siirry kirjautumiseen");

            }


        } else {
            System.out.println("Pääohjelmassa false, käyttäjää ei luotu, anna virheilmoitus");
        }
    }

}