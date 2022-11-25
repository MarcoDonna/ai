package utils;

import java.util.ArrayList;

public class Utils{
    public static Double MSE(ArrayList<Double []> data, double weight, double bias){
        Double mse = 1d/data.size();
        for(Double[] row: data)
            mse += Math.pow((row[1] - weight*row[0]+bias), 2);
        return mse;
    }

    public static ArrayList<Double[]>linearData(int n, double weight, double bias, double random){
        ArrayList<Double []> data = new ArrayList<Double []>();
        for(double i = 0; i < 30; i++){
            Double[] row = new Double[2];
            row[0] = i;
            row[1] = weight * i + (bias - random/2) + Math.random() * random;
            data.add(row);
        }
        return data;
    }
}