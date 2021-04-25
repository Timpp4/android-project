package com.example.projectx.backend;

public class BmiObject {

	private String year;
	private String bmi;
	private String sex;

	public BmiObject(String year, String bmi, String sex) {
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

	@Override
	public String toString() {
		return "BmiObject{" +
				"year='" + year + '\'' +
				", bmi='" + bmi + '\'' +
				", sex='" + sex + '\'' +
				'}';
	}
}
