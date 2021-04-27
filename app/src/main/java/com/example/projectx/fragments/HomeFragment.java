package com.example.projectx.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
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

import com.example.projectx.backend.BmiCalculator;
import com.example.projectx.backend.DataObject;
import com.example.projectx.backend.readAndWrite;
import com.example.projectx.interfaces.CountriesAPI;
import com.example.projectx.backend.DateValidation;
import com.example.projectx.backend.NumberValidation;
import com.example.projectx.R;
import com.example.projectx.interfaces.BmiBackend;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements View.OnClickListener {

    LineChart mpLineChart;
    Button insert;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View paramView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //Creating dark mode state to sharedPreference memory
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        mpLineChart = (LineChart) paramView.findViewById(R.id.chart);
        mpLineChart.getAxisLeft().setEnabled(false);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.getDescription().setEnabled(false);
        mpLineChart.getXAxis().setDrawGridLines(false);
        mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        CountriesAPI ca = new CountriesAPI();

        insert = (Button) paramView.findViewById(R.id.btnInsertWeight);
        insert.setOnClickListener(this);

        Spinner spinner = paramView.findViewById(R.id.countrySpinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ca.countryList());
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryAdapter);
        spinner.setPrompt("Choose your country");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * This function listen spinner in HomeFragment and defines functions which are used
             * when country is selected or not
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yourBmi = refreshYourBmi();
                TextView tv = Objects.requireNonNull(getView()).findViewById(R.id.lastBmi);
                tv.setText(yourBmi);
                mpLineChart.invalidate();
                mpLineChart.clear();
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                String country = ca.countryList().get(position);
                dataSets = userChartRefresh();
                dataSets.add(constantChartRefresh(country));
                //Creating graphs to UI
                LineData data = new LineData(dataSets);
                mpLineChart.setData(data);
                mpLineChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return paramView;
    }

    /**
     * This function go through ArrayList dataObject and returns sorted user bmi values calculated by
     * profile data as a ArrayList
     * @param dataObject ArrayList which include date and weight as a parameters
     * @param strings List which include profile data and this function get profile height from list
     * @return Function return ArrayList userBmiVals
     */
    @NotNull
    private ArrayList<Entry> userBmiValues(@NotNull ArrayList<DataObject> dataObject, List<String> strings) {
        BmiCalculator bc = new BmiCalculator();
        ArrayList<Entry> userBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < dataObject.size(); i++){
            userBmiVals.add(new Entry(i, (float) bc.calculateBmi(dataObject.get(i).getWeight(),
                    Double.parseDouble(strings.get(1)))));
        }
        return userBmiVals;
    }

    /**
     * This function go through ArrayList dataObject and returns user weight in ArrayList
     * @param dataObject is ArrayList which includes dates and weights inserted by user
     * @return ArrayList of calculated bmis by user data
     */
    @NotNull
    private ArrayList<Entry> userWeight(@NotNull ArrayList<DataObject> dataObject) {
        ArrayList<Entry> userBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < dataObject.size(); i++){
            userBmiVals.add(new Entry(i, (float) (dataObject.get(i).getWeight())));
        }
        return userBmiVals;
    }

    /**
     * This function make ArrayList from WHO Athena API response which is used to draw constant line
     * in chart
     * @param size Size of ArrayList <DataObject>
     * @param avgBmi WHO Athena API response which include average BMI
     * @return ArrayList of average bmis from WHO Athena API
     */
    @NotNull
    private ArrayList<Entry> whoBmiConstant(int size, String[] avgBmi) {
        ArrayList<Entry> whoBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < size; i++){
            whoBmiVals.add(new Entry(i, Float.parseFloat(avgBmi[0])));
        }
        return whoBmiVals;
    }

    /**
     * This function defines user specific lines in functions and returns datasets
     * @return DataSets which is used to draw lines to chart
     */
    public ArrayList<ILineDataSet> userChartRefresh(){

        //Initializing the list for graphs
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.clear();
        readAndWrite rw = new readAndWrite(getContext());

        // Initializing BMI graphs and styles
        LineDataSet userBmiDataSet = new LineDataSet(userBmiValues(rw.readUserData
                (), rw.profileInfo()), "Oma BMI");
        userBmiDataSet.setLineWidth(2);
        userBmiDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        userBmiDataSet.setDrawCircles(true);
        userBmiDataSet.setCircleColor(Color.BLACK);
        userBmiDataSet.setCircleHoleRadius(3f);
        userBmiDataSet.setColor(Color.MAGENTA);
        userBmiDataSet.setDrawFilled(true);
        userBmiDataSet.setFillColor(Color.MAGENTA);
        userBmiDataSet.setFillAlpha(30);
        userBmiDataSet.setValueTextSize(12f);
        userBmiDataSet.setValueTextColor(Color.GRAY);
        dataSets.add(userBmiDataSet);

        LineDataSet userWeightDataSet = new LineDataSet(userWeight(rw.readUserData()), "Paino");
        userWeightDataSet.setColor(Color.GREEN);
        userWeightDataSet.setLineWidth(3f);
        userWeightDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        userWeightDataSet.setDrawCircles(false);
        userWeightDataSet.setDrawValues(true);
        userWeightDataSet.setValueTextSize(12f);
        userWeightDataSet.setValueTextColor(Color.GRAY);
        dataSets.add(userWeightDataSet);

        return dataSets;

    }

    /**
     * This function return ArrayList whoBmiDataSet which include data for drawing constant line to
     * line chart
     * @param country is value from spinner
     * @return whoBmiDataSet include data for drawing constant line
     */
    public LineDataSet constantChartRefresh(String country){
        final String YEAR = "2016";
        CountriesAPI ca = new CountriesAPI();
        BmiBackend bb = new BmiBackend();
        readAndWrite rw = new readAndWrite(getContext());
        if(bb.whoRequest(ca.countriesRequest(country))){
            bb.whoRequest(ca.countriesRequest(country));
            Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(),
                    "Country data downloaded successfully",
                    Toast.LENGTH_LONG).show();
        }
        else{
            bb.whoRequest(ca.countriesRequest("FIN"));
            Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(),
                    "Country not found from database! Finland data downloaded.",
                    Toast.LENGTH_LONG).show();
        }
        // Initializing Comparison graph and styles
        final String[] avgBmi = {""};
        if(rw.profileInfo().get(4).equals("Other")){
            avgBmi[0] = bb.getBmiFromWho("Both sexes", YEAR);
        }
        else if (rw.profileInfo().get(4).equals("Female")){
            avgBmi[0] = bb.getBmiFromWho("Female", YEAR);
        }
        else{
            avgBmi[0] = bb.getBmiFromWho("Male", YEAR);
        }
        LineDataSet whoBmiDataSet = new LineDataSet(whoBmiConstant(rw.readUserData().size(), avgBmi), "Vertailu BMI");
        whoBmiDataSet.setColor(Color.RED);
        whoBmiDataSet.setLineWidth(5f);
        whoBmiDataSet.setDrawCircles(false);
        whoBmiDataSet.setDrawValues(true);
        whoBmiDataSet.setValueTextSize(12f);
        whoBmiDataSet.setValueTextColor(Color.GRAY);
        return whoBmiDataSet;
    }

    /**
     * This function define functions used if button labeled "Insert weight" is pressed. Function
     * include also error handling
     * @param view
     */
    @Override
    public void onClick(View view) {

        EditText test = null;
        NumberValidation nv = new NumberValidation(test);
        DateValidation dv = new DateValidation();
        readAndWrite rw = new readAndWrite(getContext());
        EditText insDate = Objects.requireNonNull(getView()).findViewById(R.id.textDate);
        String date = insDate.getText().toString();
        date = dv.DateFormating(date);
        EditText insWeight = getView().findViewById(R.id.textWeight);
        double weight = nv.doubleValidation(insWeight);
        if (dv.DateValidation(date) && 0 < weight && weight < 600){
            userBmiValues(rw.readUserData(), rw.profileInfo());
            rw.insertWeight(date, weight);
            rw.readUserData();
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets = userChartRefresh();
            LineData data = new LineData(dataSets);
            mpLineChart.setData(data);
            mpLineChart.invalidate();
            Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(),
                    "Choose country to compare average BMI!",
                    Toast.LENGTH_LONG).show();
            TextView tv = getView().findViewById(R.id.lastBmi);
            tv.setText(refreshYourBmi());
        }
        else if (!dv.DateValidation(date)) {
            insDate.setError("Date isn't valid! Set date i.e 01.01.1990");
        }
        else if (weight < 0 || 600 < weight) {
            insWeight.setError("Weight must be between 0 and 600!");
        }
    }

    /**
     * This function get users last weight from sorted (by date) ArrayList and returns it
     * @return String value returned
     */
    public String refreshYourBmi () {
        readAndWrite rw = new readAndWrite(getContext());
        ArrayList<Entry> vals = userBmiValues(rw.readUserData(), rw.profileInfo());
        float lastNode = vals.get(vals.size() - 1).getY();
        @SuppressLint("DefaultLocale") String yourBmi = String.format("%.2f", lastNode);
        return yourBmi;
    }
}