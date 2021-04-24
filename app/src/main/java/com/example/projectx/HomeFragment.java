package com.example.projectx;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    LineChart mpLineChart;
    private View paramView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paramView = inflater.inflate(R.layout.fragment_home, container, false);

        /**
         * Tässä blokissa määritellään kuvaaja sekä kuvaajien tyylit
         */
        mpLineChart = (LineChart) paramView.findViewById(R.id.chart);
        mpLineChart.getAxisLeft().setEnabled(false);
        mpLineChart.getAxisRight().setEnabled(false);
        mpLineChart.getDescription().setEnabled(false);
        mpLineChart.getXAxis().setDrawGridLines(false);
        mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //Alustetaan lista esitettäville kuvaajille
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        // Oma BMI kuvaajan alustus, tyylit ja lisäys
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

        // Vertailu BMI kuvaajan alustus, tyylit ja lisäys
        LineDataSet lineDataSet2 = new LineDataSet(whoBmiConstant(), "Vertailu BMI");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setLineWidth(3f);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);
        dataSets.add(lineDataSet2);

        //Kuvaajien luonti UI:lle
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