package com.example.projectx.backend;

public class BmiCalculator {
    double weight;
    double height;
    double bmi;

    public double calculateBmi(double weight, double height){
        bmi = weight/((height/100)*(height/100));
        System.out.println("********BMI DATA: " + weight + " " +height+" " +bmi);
        return bmi;
    }

    public String bmiAnalysis(double bmi){
        String bmiAnalysis = "";
        if(bmi < 15) bmiAnalysis = "Sairaalloinen alipaino";
        if((15 <= bmi) && (bmi < 18)) bmiAnalysis = "Vaarallinen aliravitsemus";
        if((18 <= bmi) && (bmi < 19)) bmiAnalysis = "Lievä alipaino";
        if((19 <= bmi) && (bmi < 25)) bmiAnalysis = "Normaali paino";
        if((25 <= bmi) && (bmi < 30)) bmiAnalysis = "Lievä ylipaino";
        if((30 <= bmi) && (bmi < 35)) bmiAnalysis = "Merkittänä ylipaino";
        if((35 <= bmi) && (bmi < 40)) bmiAnalysis = "Vaikea ylipaino";
        if(40 <= bmi) bmiAnalysis = "Sairaalloinen ylipaino";
        return bmiAnalysis;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getBmi(double weight, double height) {
        return calculateBmi(weight, height);
    }
    public String getAnalysis(double bmi){
        return bmiAnalysis(bmi);
    }
}
