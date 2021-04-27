package com.example.projectx.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.projectx.LoginSignUp;
import com.example.projectx.backend.PasswordValidator;
import com.example.projectx.backend.NumberValidation;
import com.example.projectx.R;
import com.example.projectx.backend.readAndWrite;

import java.util.Objects;


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

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        //Creating dark mode state to sharedPreference memory
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

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

        EditText test = null;

        readAndWrite rw = new readAndWrite(getContext());
        NumberValidation nv = new NumberValidation(test);

        EditText tv_user = getView().findViewById(R.id.et_username);
        System.out.println(tv_user.getText().toString());

        EditText tv_pass = getView().findViewById(R.id.et_password);
        String password = tv_pass.getText().toString();
        if (!PasswordValidator.isValid(password)){
            password = "";
        }
        System.out.println(tv_pass.getText().toString());

        EditText tv_height = getView().findViewById(R.id.et_height);
        System.out.println(tv_height.getText().toString());
        double height = nv.doubleValidation(tv_height);
        System.out.println(height);

        EditText tv_weight = getView().findViewById(R.id.et_weight);
        System.out.println(tv_weight.getText().toString());
        //double weight = Double.valueOf(tv_weight.getText().toString());
        double weight = nv.doubleValidation(tv_weight);
        System.out.println(weight);

        EditText tv_age = getView().findViewById(R.id.et_age);
        System.out.println(tv_weight.getText().toString());
        int age = nv.integerValidation(tv_age);
        System.out.println(age);

        Spinner tv_sex = (Spinner) getView().findViewById(R.id.et_sexSpinner);
        String gender = tv_sex.getSelectedItem().toString();
        System.out.println(gender);

        if (rw.createNewUser(tv_user.getText().toString(),
                password,
                height,
                weight,
                age,
                gender)) {

            if (view.getId() == signUpButton.getId()) {
                ((LoginSignUp) getActivity()).navigateToLogin();
                System.out.println("pääohjelmassa true, siirry kirjautumiseen");

            }

        }
        else if (tv_user.getText().toString().trim().length() == 0){
            tv_user.setError("Invalid username!");
        }
        else if (tv_pass.getText().toString() == null || !PasswordValidator.isValid(tv_pass.getText().toString())) {
            tv_pass.setError("Invalid password");
        /*else if (tv_pass.getText().toString() == null || tv_pass.getText().toString().length() < 12) {
            tv_pass.setError("Password must be at least 12 characters!");*/
        }
        else if (height < 0 || 300 < height) {
            tv_height.setError("Height must be between 0 and 300!");
        }
        else if (weight < 0 || 600 < weight) {
            tv_weight.setError("Weight must be between 0 and 600!");
        }
        else if (age < 1900 || 2022 < age) {
            tv_age.setError("Year of born must be between 1900 and 2022!");
        }
        else {
            System.out.println("Pääohjelmassa false, käyttäjää ei luotu, anna virheilmoitus");
        }
    }

}
