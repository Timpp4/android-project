package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.TimeZone;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment implements View.OnClickListener {

    LineChart mpLineChart;
    Button insert;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        BmiBackend bb = new BmiBackend();
        CountriesAPI ca = new CountriesAPI();
        bb.whoRequest();
        System.out.println("********" + bb.getBmiFromWho("Male", "2016"));
        View paramView = inflater.inflate(R.layout.fragment_home, container, false);
        insert = (Button) paramView.findViewById(R.id.btnInsertWeight);
        insert.setOnClickListener(this);

        Spinner spinner = paramView.findViewById(R.id.countrySpinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ca.countryList());
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Parent: " +parent+ " position: " +position+ " id: "+id);
                String country = ca.countryList().get(position);
                ca.countriesRequest(country);
                System.out.println("**************************VALITTU MAA: " +country);
                //ca.countriesRequest(country);
                //stuff here to handle item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * Tässä blokissa määritellään kuvaaja sekä kuvaajien tyylit
         */
        mpLineChart = (LineChart) paramView.findViewById(R.id.chart);
        mpLineChart.getAxisLeft().setEnabled(false);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.getDescription().setEnabled(false);
        mpLineChart.getXAxis().setDrawGridLines(false);
        mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //Initializing the list for graphs
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        // Initializing BMI graphs and styles
        LineDataSet lineDataSet1 = new LineDataSet(userBmiValues(), "Oma BMI");
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setCircleColor(Color.BLACK);
        lineDataSet1.setCircleHoleRadius(3f);
        lineDataSet1.setColor(Color.MAGENTA);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setFillColor(Color.MAGENTA);
        lineDataSet1.setFillAlpha(30);
        lineDataSet1.setValueTextSize(12f);
        dataSets.add(lineDataSet1);

        // Initializing Comparison graph and styles
        LineDataSet lineDataSet2 = new LineDataSet(whoBmiConstant(), "Vertailu BMI");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setLineWidth(3f);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);
        dataSets.add(lineDataSet2);

        //Creating graphs to UI
        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        return paramView;
    }

    @NotNull
    private ArrayList<Entry> userBmiValues()
    /**
     * Tässä aliohjelmassa luetaan käyttän painodata ja muunnetaan BMI:ksi BMI luokan avulla
     */
    {
        //TODO: Lisää profiililta paino ja päivänmäärä data tähän. (x=bmi, y= pvm)
        ArrayList<Entry> userBmiVals = new ArrayList<Entry>();
        userBmiVals.add(new Entry(0, 20));
        userBmiVals.add(new Entry(1, 24));
        userBmiVals.add(new Entry(2, 5));
        userBmiVals.add(new Entry(3, 10));
        userBmiVals.add(new Entry(4, 25));

        return userBmiVals;
    }

    //Run dates from userdata and search country-specific average BMI from WHO API
    @NotNull
    private ArrayList<Entry> whoBmiConstant()
    /**
     * Tässä aliohjelmassa ajetaan käyttäjätiedoista päivämäärät ja asetetaan WHO AthenaAPIsta
     * vuoden ja sukupuolen mukaan haettu maakohtainen keskibmi
     */
    {
        //TODO: Lisää profiililta paino ja päivänmäärä data tähän.
        ArrayList<Entry> whoBmiVals = new ArrayList<Entry>();
        //TODO: for-looppi, jolla lisätään pvmt x ja whoAPI tieto vakiona y
        whoBmiVals.add(new Entry(0, 25));
        whoBmiVals.add(new Entry(1, 25));
        whoBmiVals.add(new Entry(2, 25));
        whoBmiVals.add(new Entry(3, 25));
        whoBmiVals.add(new Entry(4, 25));

        return whoBmiVals;
    }

    @Override
    public void onClick(View view) {
        EditText test = null;
        NumberValidation nv = new NumberValidation(test);
        DateValidation dv = new DateValidation();
        EditText insDate = getView().findViewById(R.id.textDate);
        String date = insDate.getText().toString();
        System.out.println(dv.DateValidation(date));
        System.out.println(date);
        EditText insWeight = getView().findViewById(R.id.textWeight);
        double weight = nv.doubleValidation(insWeight);
        System.out.println(weight);
        if (dv.DateValidation(date) && 0 < weight && weight < 600){
            //Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(), bb., Toast.LENGTH_LONG).show();
            //TODO: Lisää lokiin
        }
        else if (!dv.DateValidation(date)) {
            insDate.setError("Date isn't valid");
        }
        else if (weight < 0 || 600 < weight) {
            insWeight.setError("Weight must be between 0 and 600!");
        }
    }
}