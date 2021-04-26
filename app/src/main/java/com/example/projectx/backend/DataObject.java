package com.example.projectx.backend;

import java.util.Date;

public class DataObject implements Comparable<DataObject> {

    private Date date;
    private double weight;

    /**
     * This function defines DataObject which is used to make ArrayList from weight data inserted by user
     */
    public DataObject(Date date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    /**
     * This function override toString method for DataObject
     * @return values in string
     */
    @Override
    public String toString() {
        return "DataObject{" +
                "date=" + date +
                ", weight=" + weight +
                '}';
    }

    public Date getDateTime() {
        return date;
    }

    public double getWeight() {
        return weight;
    }

    public void setDateTime(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(DataObject o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
