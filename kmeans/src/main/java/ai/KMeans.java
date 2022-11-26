package ai;

import java.util.ArrayList;

public class KMeans{
    //set initial centroids ok
    //split data based on euclidean distance to centroids ok
    //Find new centroids ok
        //Average of each feature ok
            //Extract feauture from group   ok
            //FInd average of feture ok
    //Back to 2
    ArrayList<Double[]> data = new ArrayList<Double []>();

    public KMeans setData(ArrayList<Double []> data){
        this.data = data;
        return this;
    }

    public ArrayList<ArrayList<Double[]>> fit(int nGroups, int epochs){
        ArrayList<Double []> centroids = new ArrayList<Double []>();
        ArrayList<ArrayList<Double[]>> groups = new ArrayList<ArrayList<Double[]>>();

        for(int i = 0; i < nGroups; i++)
            centroids.add(data.get((int)Math.floor(Math.random() * data.size())));          
            
        
        for(int i = 0; i < epochs; i++){
            groups = split(centroids);
            for(int j = 0; j < groups.size(); j++)
                centroids.set(j, findCentroid(groups.get(j)));
        }

        debugCentroids(centroids);
        debugGroups(groups);

        return groups;        
    }

    private ArrayList<ArrayList<Double[]>> split(ArrayList<Double[]> centroids){
        ArrayList<ArrayList<Double[]>> groups = new ArrayList<ArrayList<Double[]>>();
        for(Double[] centroid: centroids)
            groups.add(new ArrayList<Double []>());

        for(Double[] item: data){
            int minIndex = 0;
            double minDistance = euclideanDistance(item, centroids.get(0));
            for(int i = 1; i < centroids.size(); i++){
                double distance = euclideanDistance(item, centroids.get(i));
                if(distance < minDistance){
                    minDistance = distance;
                    minIndex = i;
                }
            }
            groups.get(minIndex).add(item);
        }
        return groups;
    }

    private Double[] findCentroid(ArrayList<Double[]> group){
        Double[] centroid= new Double[group.get(0).length];        
        for(int i = 0; i < group.get(0).length; i++)
            centroid[i] = vectorMeanValue(extractFeature(group, i));
        return centroid;
    }

    private Double euclideanDistance(Double[] a, Double[] b){
        double distance = 0;
        for(int i = 0; i < a.length; i++)
            distance += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(distance);
    }

    private ArrayList<Double> extractFeature(ArrayList<Double[]> data, int index){
        ArrayList<Double> feature = new ArrayList<Double>();
        for(Double[] item: data)
            feature.add(item[index]);
        return feature;
    }

    private Double vectorMeanValue(ArrayList<Double> data){
        double mean = 0;
        for(Double item: data)
            mean += item;
        return mean/data.size();
    }

    private void debugGroups(ArrayList<ArrayList<Double[]>> groups){
        for(ArrayList<Double[]> group: groups){
            System.out.println("g:");
            for(Double[] item: group)
                System.out.println("" + item[0] + ", " + item[1]);
        }
    }

    private void debugCentroids(ArrayList<Double[]> centroids){
        for(Double[] centroid: centroids)
            System.out.println("centroid:" + centroid[0] + ", " + centroid[1]);
    }
}