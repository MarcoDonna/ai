import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import ai.NBC;
import utils.*;
import utils.metrics.*;

public class NBCTest{
    @Test
    public void testNBC(){
        ArrayList<ArrayList<String>> importedData = Utils.loadCSV("src/test/resources/titanic.csv");

        importedData = Utils.removeRecord(importedData, 0);

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for(ArrayList<String> row: importedData){
            ArrayList<String> newRow = new ArrayList<>();
            newRow.add(row.get(2));
            newRow.add(row.get(5));
            if(!row.get(6).equals(""))
                newRow.add(Double.parseDouble(row.get(6)) < 18 ? "Kid" : "Adult");
            newRow.add(row.get(1));
            if(!row.get(6).equals(""))
                data.add(newRow);
        }

        data = Utils.shuffle(data);

        ArrayList<ArrayList<String>> train = Utils.head(data, 0.7);
        ArrayList<ArrayList<String>> test = Utils.head(Utils.invert(data), 0.3);

        ArrayList<String> trainClasses = Utils.extractFeatureString(train, 3);
        train = Utils.removeFeature(train, 3);

        ArrayList<String> testClasses = Utils.extractFeatureString(test, 3);
        test = Utils.removeFeature(test, 3);

        NBC nbc = new NBC().setData(train, trainClasses);
        
        ArrayList<String> predicted = new ArrayList<>();

        for(int i = 0; i < test.size(); i++){
            predicted.add(nbc.classify(test.get(i)));
        }

        ArrayList<ArrayList<Integer>> matrix = Metrics.confusionMatrix(testClasses, predicted);

        double macroPrecision = 0;
        for(double p: Metrics.precision(matrix))
            macroPrecision+=p;
        assertTrue(macroPrecision/matrix.size() > 0.8, "NBC Macro precision above 0.8 (" + macroPrecision/matrix.size() + ")");    
    }
}