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

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat testFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date dateValidation = null;

        try{
            dateValidation = testFormat.parse(date);
        }catch (ParseException e){
            return false;
        }
        assert dateValidation != null;
        return testFormat.format(dateValidation).equals(date);
    }

    public String DateFormating (String date){
        try {
            date = date.replaceAll("/", ".").replaceAll("-", ".");
            if (date.charAt(1) == '.') date = "0" + date;
            if (date.charAt(4) == '.') date = date.substring(0,3) + "0" + date.substring(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
