	
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import ai.LinearRegression;
import utils.*;
import utils.metrics.*;

class LinearRegressionTest{
    @Test
    public void testFit(){
        ArrayList<Double[]> data = Utils.linearData(20, 4.5, -3.2, 0.5);

        LinearRegression lr = new LinearRegression();        
        HashMap<String, Double> res = lr.setData(data).fit(10000000, 0.0001);
        
        double weight = res.get("weight"), 
            bias = res.get("bias");

        double initialMSE = Metrics.MSE(data, 1d, 0d), 
            finalMSE = Metrics.MSE(data, weight, bias);

        assertTrue(finalMSE < initialMSE, "Decrease MSE");
        assertTrue(4.4 < weight && weight < 4.6, "Weight between 4.4 and 4.6");
        assertTrue(-3.3 < bias && bias < .3-1, "Weight between -3.3 and -3.1");
    }
}