package com.example.projectx;

public class bmiObject {

	private String year;
	private String bmi;
	private String sex;

	public bmiObject(String year, String bmi, String sex) {
		this.year = year;
		this.bmi = bmi;
		this.sex = sex;
	}

	public String getBmi() {
		return bmi;
	}

	public String getSex() {
		return sex;
	}

	public String getYear() {
		return year;
	}
}
