import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import ai.DTNode;
import utils.Utils;

public class DecisionTreeTest{
    @Test
    public void testFit(){
        ArrayList<ArrayList<String>> data = Utils.loadCSV("src/test/resources/iris.csv");

        data = Utils.removeRecord(data, 0);
        data = Utils.shuffle(data);

        ArrayList<ArrayList<String>> train = Utils.head(data, 0.7);
        ArrayList<ArrayList<String>> test = Utils.head(Utils.invert(data), 0.3);

        ArrayList<String> trainClasses =Utils.extractFeatureString(train, 4);
        ArrayList<ArrayList<String>> trainFeatures = Utils.removeFeature(train, 4);

        DTNode rootNode = new DTNode().setData(trainFeatures, trainClasses).fit(10);

        ArrayList<String> actual = new ArrayList<>();
        ArrayList<String> predicted = new ArrayList<>();

        for(int i = 0; i < test.size(); i++){
            actual.add(test.get(i).get(4));
            predicted.add(rootNode.classify(test.get(i)));
        }

        ArrayList<ArrayList<Integer>> matrix = Utils.confusionMatrix(actual, predicted);

        double macroF1Score = 0;
        for(double score: Utils.f1Score(matrix))
            macroF1Score += score;
        
        macroF1Score/=matrix.size();

        assertTrue(macroF1Score >= 0.9, "Model f1 score above 0.9 (" + macroF1Score + ")");

        
        /*System.out.println("Precision:");
        double [] precision = Utils.precision(matrix);
        for(double p: precision)
            System.out.print("|" + p + "|");

        System.out.println("\nRecall:");    
        double[] recall = Utils.recall(matrix);
        for(double p: recall)
            System.out.print("|" + p + "|");
        
        System.out.println("\nF1 Score:");

        double[] f1 = Utils.f1Score(matrix);
        for(double p: f1)
            System.out.print("|" + p + "|");

        double macrof1score = */

    }
}