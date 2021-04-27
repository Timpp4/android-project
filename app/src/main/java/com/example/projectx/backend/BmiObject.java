package com.example.projectx.backend;

public class BmiObject {

	private final String year;
	private final String bmi;
	private final String sex;

	/**
	 * This function defines BmiObject which is used to make ArrayList from calculated bmis
	 */
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

	/**
	 * This function override toString method for DataObject
	 * @return values in string
	 */
	@Override
	public String toString() {
		return "BmiObject{" +
				"year='" + year + '\'' +
				", bmi='" + bmi + '\'' +
				", sex='" + sex + '\'' +
				'}';
	}
}
