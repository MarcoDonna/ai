package utils.metrics;

import utils.*;

import java.util.ArrayList;

public class Metrics{
    public static Double MSE(ArrayList<Double []> data, double weight, double bias){
        Double mse = 1d/data.size();
        for(Double[] row: data)
            mse += Math.pow((row[1] - weight*row[0]+bias), 2);
        return mse;
    }

    public static double informationEntropy(ArrayList<String> data){
        double entropy = 0;
        for(String classValue: Utils.distinct(data)){
            double prob = Utils.probability(data, classValue);
            entropy += prob * (Math.log10(prob) / Math.log10(2));
        }
        return -entropy;
    }

    public static ArrayList<ArrayList<Integer>> confusionMatrix(ArrayList<String> actual, ArrayList<String> predicted){
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        ArrayList<String> distinctValues = Utils.distinct(actual);

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

        return matrix;            
    }

    public static double[] precision(ArrayList<ArrayList<Integer>> confusionMatrix){
        double[] ret = new double[confusionMatrix.size()];
        for(int i = 0; i < confusionMatrix.size(); i++){
            int truePositive = confusionMatrix.get(i).get(i);
            int predictedPositive = 0;
            for(int j = 0; j < confusionMatrix.size(); j++)
                predictedPositive += confusionMatrix.get(j).get(i);
            ret[i] = (double)truePositive/predictedPositive;
        }
        return ret;            
    }

    public static double[] recall(ArrayList<ArrayList<Integer>> confusionMatrix){
        double[] ret = new double[confusionMatrix.size()];
        for(int i = 0; i < confusionMatrix.size(); i++){
            int truePositive = confusionMatrix.get(i).get(i);
            int actualPositive = 0;
            for(int j = 0; j < confusionMatrix.get(i).size(); j++)
                actualPositive += confusionMatrix.get(i).get(j);
            ret[i] = (double)truePositive/actualPositive;
        }
        return ret;            
    }

    public static double[] f1Score(ArrayList<ArrayList<Integer>> confusionMatrix){
        double[] ret = new double[confusionMatrix.size()];

        double[] precision = precision(confusionMatrix);
        double[] recall = recall(confusionMatrix);

        for(int i = 0; i < confusionMatrix.size(); i++)
            ret[i] = 2 * (precision[i]*recall[i] / (precision[i] + recall[i]));

        return ret;
    }    
}