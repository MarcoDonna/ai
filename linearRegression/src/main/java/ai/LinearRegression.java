package ai;

import java.util.HashMap;
import java.util.ArrayList;

public class LinearRegression{
    ArrayList<Double []> data = new ArrayList<Double []>();
    
    public LinearRegression setData(ArrayList<Double []> data){
        this.data = data;  
        return this;
    }

    public HashMap<String, Double> fit(int epochs, double learningRate){
        double weight = 1, bias = 0;
        for(int i = 0; i < epochs; i++){
            double derivativeW = 0, derivativeB = 0;
            for(Double[] row: data){
                derivativeW += row[0] * (weight * row[0] + bias - row[1]);
                derivativeB += weight * row[0] + bias - row[1];
            }
            weight -= learningRate*(derivativeW/data.size());
            bias -= learningRate*(derivativeB/data.size());
        }

        HashMap<String, Double> ret = new HashMap<String, Double>();
        ret.put("weight", weight);
        ret.put("bias", bias);
        return ret;
    }
}