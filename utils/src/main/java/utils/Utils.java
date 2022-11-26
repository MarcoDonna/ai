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

    public static ArrayList<Double []> clusterData(int clusters, int points, double min, double max, double xDeviation, double yDeviation){
        ArrayList<Double[]> data = new ArrayList<Double[]>();
            for(int i = 0; i < clusters; i++){
                double centerX = min + Math.random() * (max - min);
                double centerY = min + Math.random() * (max - min);
                for(int j = 0; j < points; j++){
                    Double[] point = new Double[2];
                    point[0] = centerX - xDeviation/2 + Math.random() + xDeviation;
                    point[1] = centerY - xDeviation/2 + Math.random() + yDeviation;
                    data.add(point);
                }
            }
        return data;
    }

    public static Double euclideanDistance(Double[] a, Double[] b){
        double distance = 0;
        for(int i = 0; i < a.length; i++)
            distance += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(distance);
    }

    public static ArrayList<Double> extractFeature(ArrayList<Double[]> data, int index){
        ArrayList<Double> feature = new ArrayList<Double>();
        for(Double[] item: data)
            feature.add(item[index]);
        return feature;
    }

    public static Double vectorMeanValue(ArrayList<Double> data){
        double mean = 0;
        for(Double item: data)
            mean += item;
        return mean/data.size();
    }
}