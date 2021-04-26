package com.example.projectx.backend;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class validates dates given by user in HomeFragment before writing it to userdata file
 */
public class DateValidation {

    public boolean DateValidation(String date){

        date = date.replaceAll("/", ".").replaceAll("-", ".");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat testFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date dateValidation = null;
        try{
            dateValidation = testFormat.parse(date);
        }catch (ParseException e){
            return false;
        }
        return testFormat.format(dateValidation).equals(date);
    }
}
