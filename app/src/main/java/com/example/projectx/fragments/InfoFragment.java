package com.example.projectx.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectx.R;
import com.example.projectx.backend.readAndWrite;

import java.util.ArrayList;
import java.util.List;


public class InfoFragment extends Fragment implements View.OnClickListener {

    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        submit = (Button) v.findViewById((R.id.info_submit));
        submit.setOnClickListener(this);
        displayData(v);

        return v;
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == submit.getId()) {
            readAndWrite rw = new readAndWrite(getContext());
            TextView tvId = getView().findViewById(R.id.info_id_remove);
            TextView tvData = getView().findViewById(R.id.info_data);
            System.out.println(tvId.getText().toString());
            int id = Integer.parseInt(tvId.getText().toString());
            tvData.setText(rw.removeData(id));






        }
    }

    // Display user data
    public void displayData(View view) {
        readAndWrite rw = new readAndWrite(getContext());
        String contents = rw.displayData();
        TextView tv = view.findViewById(R.id.info_data);
        tv.setText(contents);
    }





}