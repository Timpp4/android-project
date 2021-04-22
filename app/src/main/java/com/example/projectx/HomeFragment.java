package com.example.projectx;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    LineChart mpLineChart;
    private View paramView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paramView = inflater.inflate(R.layout.fragment_home, container, false);
        mpLineChart = (LineChart) paramView.findViewById(R.id.chart);

        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        return paramView;
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 5));
        dataVals.add(new Entry(3, 10));
        dataVals.add(new Entry(4, 25));

        return dataVals;
    }
}