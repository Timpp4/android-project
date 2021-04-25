package com.example.projectx.fragments;

import android.annotation.SuppressLint;
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
        testi (paramView);
        /**
         * Tässä blokissa määritellään kuvaaja sekä kuvaajien tyylit
         */
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
        System.out.println("****** NAPIN PAINALLUS : " + insert.isPressed());

        Spinner spinner = paramView.findViewById(R.id.countrySpinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ca.countryList());
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(countryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        String test = spinner.getSelectedItem().toString();
        System.out.println("************************ TÄMÄ ON userChartRefresh **************" +test);
        return paramView;
    }

    @NotNull
    private ArrayList<Entry> userBmiValues(ArrayList<DataObject> dataObject, List<String> strings)
    /**
     * Tässä aliohjelmassa luetaan käyttän painodata ja muunnetaan BMI:ksi BMI luokan avulla
     */
    {
        BmiCalculator bc = new BmiCalculator();
        ArrayList<Entry> userBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < dataObject.size(); i++){
            userBmiVals.add(new Entry(i, (float) bc.calculateBmi(dataObject.get(i).getWeight(),
                    Double.parseDouble(strings.get(1)))));
        }
        return userBmiVals;
    }
    @NotNull
    private ArrayList<Entry> userWeight(ArrayList<DataObject> dataObject)
    /**
     * Tässä aliohjelmassa luetaan käyttän painodata ja muunnetaan BMI:ksi BMI luokan avulla
     */
    {
        ArrayList<Entry> userBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < dataObject.size(); i++){
            userBmiVals.add(new Entry(i, (float) (dataObject.get(i).getWeight())));
        }
        return userBmiVals;
    }

    //Run dates from userdata and search country-specific average BMI from WHO API
    @NotNull
    private ArrayList<Entry> whoBmiConstant(int size, String[] avgBmi)
    /**
     * Tässä aliohjelmassa ajetaan käyttäjätiedoista päivämäärät ja asetetaan WHO AthenaAPIsta
     * vuoden ja sukupuolen mukaan haettu maakohtainen keskibmi
     */
    {
        ArrayList<Entry> whoBmiVals = new ArrayList<Entry>();
        for (int i = 0; i < size; i++){
            whoBmiVals.add(new Entry(i, Float.parseFloat(avgBmi[0])));
        }
        return whoBmiVals;
    }
    public ArrayList<ILineDataSet> userChartRefresh(){

        BmiBackend bb = new BmiBackend();


        //Initializing the list for graphs
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.clear();
        readAndWrite rw = new readAndWrite(getContext());

        // Initializing BMI graphs and styles
        LineDataSet lineDataSet1 = new LineDataSet(userBmiValues(rw.readUserData
                (), rw.profileInfo()), "Oma BMI");
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
        lineDataSet1.setValueTextColor(Color.GRAY);
        dataSets.add(lineDataSet1);

        LineDataSet lineDataSet3 = new LineDataSet(userWeight(rw.readUserData()), "Paino");
        lineDataSet3.setColor(Color.GREEN);
        lineDataSet3.setLineWidth(3f);
        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawValues(true);
        lineDataSet3.setValueTextSize(12f);
        lineDataSet3.setValueTextColor(Color.GRAY);
        dataSets.add(lineDataSet3);

        return dataSets;

    }

    public LineDataSet constantChartRefresh(String country){
        final String YEAR = "2016";
        CountriesAPI ca = new CountriesAPI();
        BmiBackend bb = new BmiBackend();
        readAndWrite rw = new readAndWrite(getContext());
        if(bb.whoRequest(ca.countriesRequest(country))){
            bb.whoRequest(ca.countriesRequest(country));
        }
        else{
            bb.whoRequest(ca.countriesRequest("FIN"));
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
        LineDataSet lineDataSet2 = new LineDataSet(whoBmiConstant(rw.readUserData().size(), avgBmi), "Vertailu BMI");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setLineWidth(5f);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(true);
        lineDataSet2.setValueTextSize(12f);
        lineDataSet2.setValueTextColor(Color.GRAY);
        return lineDataSet2;
    }

    @Override
    public void onClick(View view) {

        EditText test = null;
        NumberValidation nv = new NumberValidation(test);
        DateValidation dv = new DateValidation();
        readAndWrite rw = new readAndWrite(getContext());
        BmiCalculator bv = new BmiCalculator();
        EditText insDate = getView().findViewById(R.id.textDate);
        String date = insDate.getText().toString();
        System.out.println(dv.DateValidation(date));
        System.out.println(date);
        EditText insWeight = getView().findViewById(R.id.textWeight);
        double weight = nv.doubleValidation(insWeight);
        System.out.println(weight);
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
        }
        else if (!dv.DateValidation(date)) {
            insDate.setError("Date isn't valid");
        }
        else if (weight < 0 || 600 < weight) {
            insWeight.setError("Weight must be between 0 and 600!");
        }
    }
    @SuppressLint("SetTextI18n")
    public void testi(View view) {
        //TODO: Lisää viimeisin arvo TextViewiin
        TextView tv = (TextView) view.findViewById(R.id.lastBmi);
        tv.setText("text");
    }
}