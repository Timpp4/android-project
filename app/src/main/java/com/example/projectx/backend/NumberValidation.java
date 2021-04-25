package com.example.projectx.backend;

import android.widget.EditText;

public class NumberValidation {
    EditText test;
    public NumberValidation(EditText test) {
        this.test = test;
    }
    public double doubleValidation (EditText test){
        double testValue = -1;
        if (!test.getText().toString().trim().equals("") || !test.getText().toString().isEmpty()){
            testValue = Double.parseDouble(test.getText().toString());
        }
        return testValue;
    }
    public int integerValidation (EditText test){
        int testValue = -1;
        if (!test.getText().toString().trim().equals("") || !test.getText().toString().isEmpty()){
            testValue = Integer.parseInt(test.getText().toString());
        }
        return testValue;
    }
}
