package com.example.projectx.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button logOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
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
            getActivity().finish();
        }
    }

    // Update profile information
    public void update(View view) {
        readAndWrite rw = new readAndWrite(getContext());
        List<String> infoList = new ArrayList<String>();
        infoList = rw.profileInfo();
        TextView tv1 = view.findViewById(R.id.profile_username);
        TextView tv2 = view.findViewById(R.id.profile_weight);
        TextView tv3 = view.findViewById(R.id.profile_height);
        TextView tv4 = view.findViewById(R.id.profile_yearBorn);
        TextView tv5 = view.findViewById(R.id.profile_sex);
        tv1.setText(infoList.get(0)); //username
        tv2.setText("Weight: " + infoList.get(4) + " kg"); //weight
        tv3.setText("Height: " + infoList.get(1) + " cm"); //height
        tv4.setText("Year of birth: " + infoList.get(2)); //yearBorn
        tv5.setText("Gender: " + infoList.get(3)); //sex
    }
}

