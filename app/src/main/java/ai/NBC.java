package ai;

import java.util.ArrayList;

import utils.Utils;

public class NBC{
    ArrayList<ArrayList<String>> predictors = new ArrayList<>();
    ArrayList<String> classes = new ArrayList<>();

    public NBC setData(ArrayList<ArrayList<String>> predictors, ArrayList<String> classes){
        this.predictors = predictors;
        this.classes = classes;
        return this;
    }

    public String classify(ArrayList<String> features){        
        ArrayList<String> distinctValues = Utils.distinct(classes);
        String prediction = "";
        double predictionProbability = -1;

        for(String dClass: distinctValues){
            //P(y | X) = P(y) * P(X | y) / P(X) --> y = class, X = predictors
            double probability = 1;      
            for(int i = 0; i < features.size(); i++){
                int countFeatureGivenClass = 0, countClass = 0;
                for(int j = 0; j < classes.size(); j++){
                    //find P(X | y);
                    if(classes.get(j).equals(dClass)){
                        countClass++;
                        if(predictors.get(j).get(i).equals(features.get(i)))
                            countFeatureGivenClass++;
                    }
                }
                probability *= Utils.probability(classes, dClass) * ((double)countFeatureGivenClass/countClass) / Utils.probability(Utils.extractFeatureString(predictors, i), features.get(i));
            }
                
            if(probability > predictionProbability){
                predictionProbability = probability;
                prediction = dClass;
            }
        }
        return prediction; 
    }
}