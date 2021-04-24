package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    LineChart mpLineChart;
    TextView date;
    EditText weight;
    String selectedDate;
    private View paramView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        paramView = inflater.inflate(R.layout.fragment_home, container, false);

        /**
         * Tässä blokissa määritellään kuvaaja sekä kuvaajien tyylit
         */
        mpLineChart = (LineChart) paramView.findViewById(R.id.chart);
        date = (TextView) paramView.findViewById(R.id.textDate);
        weight = (EditText) paramView.findViewById(R.id.textWeight);

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



        weight.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        double weightInput = Double.parseDouble(String.valueOf(weight.getText()));
                    }
                });

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
}