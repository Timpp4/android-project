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
import android.widget.Button;
import android.widget.TextView;

import com.example.projectx.R;
import com.example.projectx.backend.readAndWrite;

import java.util.List;
import java.util.Objects;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button logOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = sharedPreferences.edit();
        //Creating dark mode state to sharedPreference memory
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        logOutButton = (Button) v.findViewById(R.id.btn_logOut);
        logOutButton.setOnClickListener(this);
        update(v);
        return v;
    }

    // Log out logic
    @Override
    public void onClick(View view) {
        if (view.getId() == logOutButton.getId()) {
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    // Update profile information
    public void update(View view) {
        readAndWrite rw = new readAndWrite(getContext());
        List<String> infoList;
        infoList = rw.profileInfo();
        TextView tv1 = view.findViewById(R.id.profile_username);
        TextView tv2 = view.findViewById(R.id.profile_weight);
        TextView tv3 = view.findViewById(R.id.profile_height);
        TextView tv4 = view.findViewById(R.id.profile_yearBorn);
        TextView tv5 = view.findViewById(R.id.profile_sex);
        tv1.setText(infoList.get(0)); //username
        String weight = "Weight: " + infoList.get(4) + " kg";
        tv2.setText(weight); //weight
        String height = "Height: " + infoList.get(1) + " cm";
        tv3.setText(height); //height
        String yearBorn = "Year of birth: " + infoList.get(2);
        tv4.setText(yearBorn); //yearBorn
        String gender = "Gender: " + infoList.get(3);
        tv5.setText(gender); //sex
    }
}

