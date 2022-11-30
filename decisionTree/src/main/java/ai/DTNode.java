package ai;

import utils.*;

import java.util.ArrayList;

public class DTNode{
    double split;
    int splitFeatureIndex;

    String predicted;

    DTNode left;
    DTNode right;

    ArrayList<ArrayList<String>> predictors = new ArrayList<>();
    ArrayList<String> classes = new ArrayList<>();

    public DTNode setData(ArrayList<ArrayList<String>> predictors, ArrayList<String> classes){
        this.predictors = predictors;
        this.classes = classes;
        return this;
    }

    public String classify(ArrayList<String> predictors){
        if(this.predicted != null)
            return this.predicted;
        else{
            double predictorValue = Double.parseDouble(predictors.get(this.splitFeatureIndex));
            if(predictorValue > split)
                return right.classify(predictors);
            return left.classify(predictors);
        }        
    }

    public DTNode fit(int it){
        int bestPredictorIndex = 0;
        double bestSplitValue = 0, bestGain = 0;
        if(predictors.size() > 0)
            for(int i = 0; i < predictors.get(0).size(); i++){
                double[] splitInfo = bestGain(i);
                if(splitInfo[1] > bestGain){
                    bestGain = splitInfo[1];
                    bestSplitValue = splitInfo[0];
                    bestPredictorIndex = i;
                }
            }

        ArrayList<ArrayList<String>> leftPredictors = new ArrayList<>();
        ArrayList<ArrayList<String>> rightPredictors = new ArrayList<>();

        ArrayList<String> leftClasses = new ArrayList<>();
        ArrayList<String> rightClasses = new ArrayList<>();

        for(int i = 0; i < classes.size(); i++){
            if(Double.parseDouble(predictors.get(i).get(bestPredictorIndex)) > bestSplitValue){
                rightPredictors.add(predictors.get(i));
                rightClasses.add(classes.get(i));
            }
            else{
                leftPredictors.add(predictors.get(i));
                leftClasses.add(classes.get(i));    
            }
        }

        this.split = bestSplitValue;
        this.splitFeatureIndex = bestPredictorIndex;

        if(it-- > 0 && Utils.informationEntropy(classes) > 0){
            right = new DTNode().setData(rightPredictors, rightClasses).fit(it);
            left = new DTNode().setData(leftPredictors, leftClasses).fit(it);
        }else{
            this.predicted = classes.get(0);
        }        

        return this;
    }

    public double[] bestGain(int featureIndex){
        double split = 0, maxGain = 0;
        for(int i = 0; i < predictors.size(); i++){
            double gain = informationGain(featureIndex,  Double.parseDouble(predictors.get(i).get(featureIndex)));
            if(gain > maxGain){
                maxGain = gain;
                split = Double.parseDouble(predictors.get(i).get(featureIndex));                
            }
        }
        double[] ret = {split, maxGain}; 
        return ret;
    }

    public double informationGain(int featureIndex, double value){
        double gain = Utils.informationEntropy(classes);
        
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();

        for(int i = 0; i < predictors.size(); i++){
            double predictorValue = Double.parseDouble(predictors.get(i).get(featureIndex));
            if(predictorValue > value)
                right.add(classes.get(i));
            else
                left.add(classes.get(i));
        }
        return gain - ((double)left.size()/classes.size()*Utils.informationEntropy(left) + (double)right.size()/classes.size()*Utils.informationEntropy(right));
    }
}