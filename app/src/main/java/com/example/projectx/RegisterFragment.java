package com.example.projectx;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

//vanha
public class RegisterFragment extends Fragment implements View.OnClickListener{
//public class RegisterFragment extends Fragment {

    Button signUpButton;
    Spinner spinner;
    public View paramView;
    //private Context context;
    //public void readAndWrite(Context context){
    //    this.context=context;
    //}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        signUpButton = (Button) v.findViewById(R.id.btn_signup);
        Spinner spinner = v.findViewById(R.id.et_sexSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.genders_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return v;
    }


    @Override
    public void onClick(View view) {
        readAndWrite rw = new readAndWrite(this);
        TextView tv_user = paramView.findViewById(R.id.et_username);
        TextView tv_pass = paramView.findViewById(R.id.et_password);
        TextView tv_height = paramView.findViewById(R.id.et_height);
        double height = Double.valueOf(tv_height.getText().toString());
        TextView tv_weight = paramView.findViewById(R.id.et_weight);
        double weight = Double.valueOf(tv_weight.getText().toString());
        TextView tv_age = paramView.findViewById(R.id.et_age);
        int age = Integer.valueOf(tv_age.getText().toString());
        TextView tv_sex = paramView.findViewById(R.id.et_sexSpinner);

        if (rw.createNewUser(tv_user.getText().toString(),
                tv_pass.getText().toString(),
                height,
                height,
                age,
                tv_sex.getText().toString()) == true) {

            System.out.println("pääohjelmassa true, siirry kirjautumiseen");
            ((LoginSignUp) getActivity()).navigateToLogin();
        } else {
            System.out.println("Pääohjelmassa false, käyttäjää ei luotu, anna virheilmoitus");
        }
    }

}