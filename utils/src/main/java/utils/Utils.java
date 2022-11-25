package utils;

import java.util.ArrayList;

public class Utils{
    public static Double MSE(ArrayList<Double []> data, double weight, double bias){
        Double mse = 1d/data.size();
        for(Double[] row: data)
            mse += Math.pow((row[1] - weight*row[0]+bias), 2);
        return mse;
    }
}