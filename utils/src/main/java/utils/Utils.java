package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static ArrayList<ArrayList<Integer>> confusionMatrix(ArrayList<String> actual, ArrayList<String> predicted){
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        ArrayList<String> distinctValues = distinct(actual);

        for(int i = 0; i < distinctValues.size(); i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0; j < distinctValues.size(); j++)
                row.add(0);
            matrix.add(row);
        }

        for(int i = 0; i < actual.size(); i++){
            int xIndex = distinctValues.indexOf(actual.get(i));
            int yIndex = distinctValues.indexOf(predicted.get(i));
            int newVal = matrix.get(xIndex).get(yIndex) + 1;
            matrix.get(xIndex).set(yIndex, newVal);
        }

        for(String item: distinctValues)
            System.out.print("|" + item + "|");
        System.out.println();
        for(ArrayList<Integer> row: matrix){
            for(Integer item: row)
                System.out.print("|" + item + "|");
            System.out.println();
        }

        return matrix;
            
    }

    public static ArrayList<String> distinct(ArrayList<String> data){
        ArrayList<String> ret = new ArrayList<>();
        for(String item: data)
            if(!ret.contains(item))
                ret.add(item);
        return ret;
    }

    public static ArrayList<ArrayList<String>> loadCSV(String path){
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(path));
            String line;
            do{
                line = buffer.readLine();
                String[] values = line.split(",");
                data.add(new ArrayList<String>(Arrays.asList(values)));
            }while(line != null);
            buffer.close();
        }catch(Exception err){
            System.out.println(err);
        }
        return data;
    }

    public static ArrayList<ArrayList<String>> head(ArrayList<ArrayList<String>> data, double n){
        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        for(int i = 0; i < Math.floor(n * data.size()); i++)
            ret.add(data.get(i));
        return ret;
    }

    public static ArrayList<ArrayList<String>> invert(ArrayList<ArrayList<String>> data){
        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        for(int i = data.size() - 1; i >= 0; i--)
            ret.add(data.get(i));
        return ret;
    }

    public static ArrayList<String> extractFeatureString(ArrayList<ArrayList<String>> data, int index){
        ArrayList<String> feature = new ArrayList<String>();
        for(ArrayList<String> row: data)
            feature.add(row.get(index));
        return feature;
    }

    public static ArrayList<ArrayList<String>> removeFeature(ArrayList<ArrayList<String>> data, int index){
        for(int i = 0; i < data.size(); i++)
            data.get(i).remove(index);
        return data;
    }

    public static ArrayList<ArrayList<String>> removeRecord(ArrayList<ArrayList<String>> data, int index){
        data.remove(index);
        return data;
    }

    public static ArrayList<ArrayList<String>> shuffle(ArrayList<ArrayList<String>> data){
        for(int i = 0; i < data.size(); i++){
            int randomIndex = (int)Math.floor(Math.random() * data.size());
            ArrayList<String> temp = data.get(i);
            data.set(i, data.get(randomIndex));
            data.set(randomIndex, temp);
        }
        return data;
    }

    public static double informationEntropy(ArrayList<String> data){
        double entropy = 0;
        for(String classValue: distinct(data)){
            double prob = probability(data, classValue);
            entropy += prob * (Math.log10(prob) / Math.log10(2));
        }
        return -entropy;
    }

    public static double probability(ArrayList<String> data, String value){
        double prob = 0;
        for(String item: data)
            if(item.equals(value))
                prob++;
        return prob/data.size();
    }
}