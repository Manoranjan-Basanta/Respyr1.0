package com.humorstech.respyr.reading;


public class BloodPressureCalculation {

    public static int finalSP(int Beats, double Hei,double Wei ,double Agg, String gen){

        double Q=4.5;

        if(gen.equals("Male") || gen.equals("male")){
            Q=5;
        }

        double ROB = 18.5;
        double ET = (364.5 - 1.23 * Beats);
        double BSA = 0.007184 * (Math.pow(Wei, 0.425)) * (Math.pow(Hei, 0.725));
        double SV = (-6.6 + (0.25 * (ET - 35)) - (0.62 * Beats) + (40.4 * BSA) - (0.51 * Agg));
        double PP = SV / ((0.013 * Wei - 0.007 * Agg - 0.004 * Beats) + 1.307);
        double MPP = Q * ROB;


        return  (int) (MPP + 3 / 2 * PP);  /// final systolic pressure
    }

    public static int finalDP(int Beats, double Hei,double Wei ,double Agg, String gen){

        double Q=4.5;

        if(gen.equals("Male") || gen.equals("male")){
            Q=5;
        }

        double ROB = 18.5;
        double ET = (364.5 - 1.23 * Beats);
        double BSA = 0.007184 * (Math.pow(Wei, 0.425)) * (Math.pow(Hei, 0.725));
        double SV = (-6.6 + (0.25 * (ET - 35)) - (0.62 * Beats) + (40.4 * BSA) - (0.51 * Agg));
        double PP = SV / ((0.013 * Wei - 0.007 * Agg - 0.004 * Beats) + 1.307);
        double MPP = Q * ROB;

        return  (int) (MPP - PP / 3); // final diastolic result
    }
}
