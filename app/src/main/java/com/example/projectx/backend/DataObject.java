package com.example.projectx.backend;

import java.util.Date;

public class DataObject {

    private Date date;
    private double weight;

    public DataObject(Date date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "date=" + date +
                ", weight=" + weight +
                '}';
    }
}
